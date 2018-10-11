package com.api.genealogy.controller;

import com.api.genealogy.model.Branch;
import com.api.genealogy.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@RestController()
@RequestMapping("/api")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/branch")
    public ResponseEntity createBranch(@RequestBody Branch branch, int genealogyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(branchService.createBranch(currentUserName, genealogyId, branch), HttpStatus.OK);
    }

    @PutMapping("/branch")
    public ResponseEntity updateBranch(@RequestBody Branch branch) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(branchService.updateBranch(currentUserName,branch), HttpStatus.OK);
    }

    @DeleteMapping("/branch")
    public ResponseEntity deleteBranch(@RequestBody int branchId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(branchService.deleteBranch(currentUserName,branchId), HttpStatus.OK);
    }

    @PostMapping("/branch/genealogy")
    public ResponseEntity getBranchesByGenealogyId(@RequestBody int genealogyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return new ResponseEntity<>(branchService.getBranchesByGenealogyId(genealogyId), HttpStatus.OK);
    }

}
