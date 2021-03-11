package com.uc.services.controller;

import static com.uc.common.Constants.SUCCESS;
import static com.uc.common.Constants.Regex.NUMBER;

import static com.uc.common.Constants.UCRequestAttributes.USER_ROLE;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uc.common.enums.UserRole;
import com.uc.common.response.BaseResponse;
import com.uc.common.response.validators.IsAuthorized;
import com.uc.services.entity.ServiceType;
import com.uc.services.request.AddServiceTypeRequest;
import com.uc.services.service.ServiceTypeService;

@Validated
@RestController
@RequestMapping("type")
public class ServiceTypeController {
	
	@Autowired
	private ServiceTypeService ucServices;
	
	@PostMapping
	public ResponseEntity<BaseResponse> addServiceType(@RequestBody AddServiceTypeRequest req,
			@IsAuthorized(authorizedRoles = UserRole.ADMIN) @RequestAttribute(USER_ROLE) String userRole) {
		Long id = ucServices.addServiceType(req);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();
		return ResponseEntity.created(location).body(new BaseResponse(SUCCESS, "service type added"));
	}
	
	@GetMapping
	public ResponseEntity<List<ServiceType>> getServiceTypes() {
		return ResponseEntity.ok().body(ucServices.getAllServiceTypes());
	}

	@GetMapping("{id}")
	public ResponseEntity<ServiceType> getServiceType(
			@Pattern(regexp = NUMBER, message = "service type id is invalid") @PathVariable String id) {
		
		ServiceType type = ucServices.getServiceTypeById(id);
		return Objects.nonNull(type) ? ResponseEntity.ok().body(type) : ResponseEntity.notFound().build();
	}
	
	

}
