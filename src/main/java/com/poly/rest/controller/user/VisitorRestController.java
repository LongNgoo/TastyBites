package com.poly.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.Order;
import com.poly.model.Visitor;
import com.poly.service.OrderService;
import com.poly.service.VisitorService;

@CrossOrigin("*")
@RestController
public class VisitorRestController {
	
	@Autowired
	VisitorService visitorService;
	
	@PutMapping("/rest/visitor/{id}")
    public ResponseEntity<Visitor> put(@PathVariable("id") Integer id, @RequestBody Visitor visitor) {
        Visitor existingVisitor = visitorService.findById(id);
        if (existingVisitor != null) {
            // Increment the views count by 1
            existingVisitor.setViews(existingVisitor.getViews() + 1);

            Visitor updatedVisitor = visitorService.update(existingVisitor);
            return ResponseEntity.ok(updatedVisitor);
        }
        return ResponseEntity.notFound().build();
    }
}
