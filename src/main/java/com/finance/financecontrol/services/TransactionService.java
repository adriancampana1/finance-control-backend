package com.finance.financecontrol.services;

import com.finance.financecontrol.domain.category.Category;
import com.finance.financecontrol.domain.transaction.Transaction;
import com.finance.financecontrol.domain.transaction.TransactionDTO;
import com.finance.financecontrol.domain.transaction.TransactionResponseDTO;
import com.finance.financecontrol.domain.transaction_category.TransactionCategory;
import com.finance.financecontrol.domain.transaction_category.TransactionCategoryId;
import com.finance.financecontrol.exceptions.ResourceNotFoundException;
import com.finance.financecontrol.exceptions.UnauthorizedException;
import com.finance.financecontrol.repositories.CategoryRepository;
import com.finance.financecontrol.repositories.TransactionCategoryRepository;
import com.finance.financecontrol.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    public void createTransaction(TransactionDTO request, String userId){
        Transaction transaction = new Transaction();
        transaction.setTitle(request.title());
        transaction.setDescription(request.description());
        transaction.setType(request.type());
        transaction.setTransaction_value(request.transaction_value());
        transaction.setTransaction_date(request.transaction_date());
        transaction.setUserId(userId);

        transactionRepository.save(transaction);

        //  faz uma iteração sobre a lista de IDs passado no body da requisição
        for(String categoryId : request.categoryIds()){
            TransactionCategoryId transactionCategoryId = new TransactionCategoryId();
            transactionCategoryId.setIdTransaction(transaction.getId());
            transactionCategoryId.setIdCategory(categoryId);

            TransactionCategory transactionCategory = new TransactionCategory();
            transactionCategory.setId(transactionCategoryId);

            transactionCategoryRepository.save(transactionCategory);
        }
    }

    public List<TransactionResponseDTO> getAllTransactionsByUserId(String userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<TransactionResponseDTO> responseList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResponseDTO response = new TransactionResponseDTO();
            response.setId(transaction.getId());
            response.setUser_id(userId);
            response.setTitle(transaction.getTitle());
            response.setDescription(transaction.getDescription());
            response.setType(transaction.getType());
            response.setTransaction_value(transaction.getTransaction_value());
            response.setTransaction_date(transaction.getTransaction_date());
            response.setUser_id(transaction.getUserId());

            // Buscar categorias associadas
            List<TransactionCategory> transactionCategories = transactionCategoryRepository.findById_IdTransaction(transaction.getId());
            List<TransactionResponseDTO.CategoryResponse> categoryResponses = new ArrayList<>();
            for (TransactionCategory transactionCategory : transactionCategories) {
                Category category = transactionCategory.getCategory();
                TransactionResponseDTO.CategoryResponse categoryResponse = new TransactionResponseDTO.CategoryResponse();
                categoryResponse.setId(category.getId());
                categoryResponse.setTitle(category.getTitle());
                categoryResponse.setDescription(category.getDescription());
                categoryResponses.add(categoryResponse);
            }
            response.setCategories(categoryResponses);

            responseList.add(response);
        }

        return responseList;
    }

    public TransactionResponseDTO getTransactionByUserId(String transactionId, String userId){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if(!transaction.getUserId().equals(userId)){
            throw new UnauthorizedException("User not authorized to view this transaction");
        }

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setUser_id(userId);
        response.setId(transaction.getId());
        response.setTitle(transaction.getTitle());
        response.setDescription(transaction.getDescription());
        response.setType(transaction.getType());
        response.setTransaction_value(transaction.getTransaction_value());
        response.setTransaction_date(transaction.getTransaction_date());

        List<TransactionCategory> transactionCategories = transactionCategoryRepository.findById_IdTransaction(transaction.getId());
        List<TransactionResponseDTO.CategoryResponse> categoryResponses = new ArrayList<>();
        for(TransactionCategory transactionCategory : transactionCategories){
            Category category = transactionCategory.getCategory();
            TransactionResponseDTO.CategoryResponse categoryResponse = new TransactionResponseDTO.CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setTitle(category.getTitle());
            categoryResponse.setDescription(category.getDescription());
            categoryResponses.add(categoryResponse);
        }

        response.setCategories(categoryResponses);
        return response;
    }

    @Transactional
    public void updateTransaction(String transactionId, TransactionDTO request, String userId){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found!"));

        if(!transaction.getUserId().equals(userId)){
            throw new UnauthorizedException("User not authorized to update this transaction");
        }

        transaction.setTitle(request.title());
        transaction.setDescription(request.description());
        transaction.setType(request.type());
        transaction.setTransaction_value(request.transaction_value());
        transaction.setTransaction_date(request.transaction_date());

        transactionRepository.save(transaction);

        transactionCategoryRepository.deleteById_IdTransaction(transactionId);

        for(String categoryId : request.categoryIds()){
            TransactionCategoryId transactionCategoryId = new TransactionCategoryId();
            transactionCategoryId.setIdTransaction(transactionId);
            transactionCategoryId.setIdCategory(categoryId);

            TransactionCategory transactionCategory = new TransactionCategory();
            transactionCategory.setId(transactionCategoryId);

            transactionCategoryRepository.save(transactionCategory);
        }
    }

    @Transactional
    public void deleteTransaction(String transactionId, String userId){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if(!transaction.getUserId().equals(userId)){
            throw new UnauthorizedException("User not authorized to delete this transaction");
        }

        transactionCategoryRepository.deleteById_IdTransaction(transactionId);

        transactionRepository.delete(transaction);
    }
}
