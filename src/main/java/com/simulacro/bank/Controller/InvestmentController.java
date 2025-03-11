package com.simulacro.bank.Controller;

import com.simulacro.bank.DTO.InvestmentDTO;
import com.simulacro.bank.model.Investment;
import com.simulacro.bank.model.InvestmentCustomer;
import com.simulacro.bank.service.InvestmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investment")
@AllArgsConstructor
@Tag(name = "Investments", description = "Database operations for investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    @GetMapping("/pageable")
    @Operation(summary = "Gets all investments", description = "Return all investment with pagination")
    public ResponseEntity<Page<InvestmentDTO>> getAllInvestments(Pageable pageable) {
        return ResponseEntity.ok(investmentService.getAllInvestments(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets investment", description = "Return investment by Id")
    public ResponseEntity<InvestmentDTO> getInvestmentById(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.getInvestmentById(id));
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Gets investments from Customer", description = "Return all investments from customer")
    public ResponseEntity<List<InvestmentCustomer>> getInvestmentByCustomerId(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.getCustomerInvestments(id));
    }

    @PostMapping("/")
    @Operation(summary = "Create investment", description = "Create a investment to be listed in the bank system")
    public ResponseEntity<InvestmentDTO> createInvestment(@RequestBody InvestmentDTO investmentDto) {
        return ResponseEntity.ok(investmentService.createInvestment(investmentDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the investments", description = "Update the informations of a investment")
    public ResponseEntity<InvestmentDTO> updateInvestment(@RequestBody InvestmentDTO investmentDto, @PathVariable Long id) {
        return ResponseEntity.ok(investmentService.updateInvestment(investmentDto, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete investment", description = "Deletes a investment by his Id")
    public ResponseEntity<String> deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
