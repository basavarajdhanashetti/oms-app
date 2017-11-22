package com.bsd.oms.entity.rest;

import java.net.URI;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bsd.oms.entity.AssetRequest;
import com.bsd.oms.entity.StoreInwardRegister.Status;
import com.bsd.oms.repo.AssetRequestRepository;

@RestController
@RequestMapping(path = "/assetrequests")
public class AssetRequestService {

	private static Logger LOG = LoggerFactory.getLogger(AssetRequestService.class);

	@Autowired
	private AssetRequestRepository assetRequestRepo;

	/**
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createAssetRequest(@RequestBody AssetRequest assetRequest) {
		LOG.debug("Create new assetRequest with (" + assetRequest.toString() + " )");
		System.out.println("Create new assetRequest with (" + assetRequest.toString() + " )");
		assetRequest = assetRequestRepo.save(assetRequest);
		Calendar cal = Calendar.getInstance();
		assetRequest.setRequestNo("REQ-" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1)
				+ "/"+ String.format("%04d", assetRequest.getId()));
		assetRequestRepo.save(assetRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(assetRequest.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 
	 * @param assetRequest
	 * @return
	 */
	@PutMapping(path = "/{assetRequestId}", consumes = "application/json")
	public ResponseEntity<AssetRequest> updateAssetRequest(@PathVariable long assetRequestId, @RequestBody AssetRequest assetRequest) {
		LOG.debug("Update assetRequest with (" + assetRequest.toString() + " )");

		assetRequest.setId(assetRequestId);
		assetRequest = assetRequestRepo.save(assetRequest);

		return ResponseEntity.ok(assetRequest);
	}

	/**
	 * 
	 * @param assetRequest
	 * @return
	 */
	@GetMapping(path = "/{assetRequestId}")
	public ResponseEntity<AssetRequest> getAssetRequest(@PathVariable long assetRequestId) {
		LOG.debug("Get assetRequest for id " + assetRequestId);

		AssetRequest assetRequest = assetRequestRepo.findOne(assetRequestId);

		return ResponseEntity.ok(assetRequest);
	}

	/**
	 * 
	 * @param assetRequest
	 * @return
	 */
	@DeleteMapping(path = "/{assetRequestId}")
	public ResponseEntity<?> deleteAssetRequest(@PathVariable long assetRequestId) {
		LOG.debug("delete assetRequest for id " + assetRequestId);

		assetRequestRepo.delete(assetRequestId);

		return ResponseEntity.noContent().build();
	}

	/**
	 * 
	 * @param assetRequest
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<AssetRequest>> getAssetRequests(@RequestParam(required = false, name = "status") Status status,
			@RequestParam(required = false, name = "userId") String userId) {
		LOG.debug("Get getAssetRequests for userId " + userId);

		List<AssetRequest> lst = null;

		if (userId != null) {
			lst = assetRequestRepo.getByUserId(userId);
		} else if (status != null) {
			lst = assetRequestRepo.getByStatus(status);
		}
		return ResponseEntity.ok(lst);
	}
}
