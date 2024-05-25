package com.finance.financecontrol.controllers.transaction;

import com.finance.financecontrol.domain.transaction.TransactionDTO;
import com.finance.financecontrol.domain.transaction.TransactionResponseDTO;
import com.finance.financecontrol.repositories.UserRepository;
import com.finance.financecontrol.services.AuthenticatedUserService;
import com.finance.financecontrol.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionsController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @PostMapping("/new")
    public ResponseEntity create(@RequestBody @Valid TransactionDTO request){
        String userId = authenticatedUserService.getCurrentUserId();
        transactionService.createTransaction(request, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(){
        String userId = authenticatedUserService.getCurrentUserId();
        List<TransactionResponseDTO> transactions = transactionService.getAllTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransaction(@PathVariable String id){
        String userId = authenticatedUserService.getCurrentUserId();
        TransactionResponseDTO transaction = transactionService.getTransactionByUserId(id, userId);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid TransactionDTO data){
        String userId = authenticatedUserService.getCurrentUserId();
        transactionService.updateTransaction(id, data, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable String id){
        String userId = authenticatedUserService.getCurrentUserId();
        transactionService.deleteTransaction(id, userId);
        return ResponseEntity.noContent().build();
    }
}
