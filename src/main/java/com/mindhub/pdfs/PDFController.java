package com.mindhub.pdfs;

import com.lowagie.text.DocumentException;
import com.mindhub.models.Account;
import com.mindhub.models.Client;
import com.mindhub.models.Transaction;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class PDFController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("api/export/transactions")
    public void exportToPDF(HttpServletResponse response, Authentication authentication, @RequestParam long id) throws DocumentException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transactions_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.getById(id);
        List<Transaction> transactions = new ArrayList<>();

        if(client.getAccounts().contains(account)){
            transactions.addAll(account.getTransactions());
        }

        TransactionPDF transactionPDF = new TransactionPDF(transactions);
        transactionPDF.export(response);
    }
}
