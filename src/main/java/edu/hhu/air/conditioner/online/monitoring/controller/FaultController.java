package edu.hhu.air.conditioner.online.monitoring.controller;

import edu.hhu.air.conditioner.online.monitoring.constant.SessionConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.UrlMappingConsts;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.FaultStateEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RepairResultEnum;
import edu.hhu.air.conditioner.online.monitoring.constant.enums.RoleEnum;
import edu.hhu.air.conditioner.online.monitoring.model.entity.AirConditioner;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Allocation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Evaluation;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Fault;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Repair;
import edu.hhu.air.conditioner.online.monitoring.model.entity.Revisit;
import edu.hhu.air.conditioner.online.monitoring.model.entity.User;
import edu.hhu.air.conditioner.online.monitoring.model.request.FaultAddRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.FaultSearchRequest;
import edu.hhu.air.conditioner.online.monitoring.model.request.FaultUpdateRequest;
import edu.hhu.air.conditioner.online.monitoring.model.response.FaultAllocationTableResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.FaultDetailResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.FaultRepairTableResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.FaultRepairedTableResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.FaultRevisitTableResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.FaultTableResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.PageResponse;
import edu.hhu.air.conditioner.online.monitoring.model.response.UserResponse;
import edu.hhu.air.conditioner.online.monitoring.service.AirConditionerService;
import edu.hhu.air.conditioner.online.monitoring.service.AllocationService;
import edu.hhu.air.conditioner.online.monitoring.service.EvaluationService;
import edu.hhu.air.conditioner.online.monitoring.service.FaultService;
import edu.hhu.air.conditioner.online.monitoring.service.RepairService;
import edu.hhu.air.conditioner.online.monitoring.service.RevisitService;
import edu.hhu.air.conditioner.online.monitoring.util.EntityTranslatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author 覃国强
 * @date 2019-02-12
 */
@Slf4j
@RestController
@RequestMapping(UrlMappingConsts.FAULT_BASE_MAPPING_V1)
public class FaultController {

    @Autowired
    private FaultService faultService;

    @Autowired
    private AirConditionerService airConditionerService;

    @Autowired
    private UserController userController;

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private RepairService repairService;

    @Autowired
    private RevisitService revisitService;

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/add")
    public Fault add(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute FaultAddRequest request) throws InstantiationException, IllegalAccessException {
        Fault translateFault = EntityTranslatorUtils.translate(request, Fault.class);
        AirConditioner airConditioner = airConditionerService.getByEquipmentId(request.getEquipmentId());
        translateFault.setReportUserId(user.getId());
        translateFault.setAirConditionerId(airConditioner.getId());
        return faultService.add(translateFault);
    }

    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id) {
        faultService.deleteById(id);
    }

    @PostMapping("/update")
    public void updateById(@ModelAttribute FaultUpdateRequest request)
            throws InstantiationException, IllegalAccessException {

        Fault translate = EntityTranslatorUtils.translate(request, Fault.class);
        faultService.updateById(translate);

    }

    @PostMapping("/{id}/state/accept/update")
    public void updateStateAcceptById(@PathVariable Long id) {
        Fault fault = Fault.builder().id(id).state(FaultStateEnum.REPAIRING).build();
        faultService.updateStateById(fault);
    }

    @PostMapping("/{id}/state/reject/update")
    public void updateStateRejectById(@PathVariable Long id) {

        Fault fault = faultService.getById(id);

        Fault rejectFault = Fault.builder().id(id).state(FaultStateEnum.END).build();
        faultService.updateStateById(rejectFault);

        rejectFault = Fault.builder().id(id).repairResult(RepairResultEnum.REPAIRMAN_REJECT).build();
        faultService.updateRepairResultById(rejectFault);

        fault.setId(null);
        fault.setRepairResult(null);

        faultService.add(fault);

    }

    /**
     * 根据用户角色查询故障信息 （不分页）
     *
     * @param user 当前登录用户
     * @return 查询到的故障信息
     */
    @GetMapping
    public List<FaultTableResponse> listByUserRole(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {
        List<Fault> list;
        if (RoleEnum.REPAIRMAN.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            list = faultService.listAll();
        } else {
            list = faultService.listAllByReportUserId(user.getId());
        }
        List<FaultTableResponse> result =
                list.stream().map(this::fault2FaultTableResponse).collect(Collectors.toList());
        return result;
    }

    private FaultTableResponse fault2FaultTableResponse(Fault fault) {
        FaultTableResponse response = new FaultTableResponse();
        BeanUtils.copyProperties(fault, response);
        response.setType(fault.getType().getMessage());
        response.setState(fault.getState().getMessage());

        AirConditioner airConditioner = airConditionerService.getById(fault.getAirConditionerId());
        response.setEquipmentId(airConditioner.getEquipmentId());

        return response;
    }

    @GetMapping("/{id}")
    public FaultDetailResponse getById(@PathVariable Long id) throws IllegalAccessException, InstantiationException {
        Fault fault = faultService.getById(id);
        FaultDetailResponse response = fault2FaultDetailResponse(fault);
        return response;
    }

    private FaultDetailResponse fault2FaultDetailResponse(Fault fault)
            throws InstantiationException, IllegalAccessException {
        FaultDetailResponse translate = EntityTranslatorUtils.translate(fault, FaultDetailResponse.class);

        AirConditioner airConditioner = airConditionerService.getById(fault.getAirConditionerId());
        UserResponse userResponse = userController.get(fault.getReportUserId());
        Allocation allocation = allocationService.getByFaultId(fault.getId());
        Repair repair = repairService.getByFaultId(fault.getId());
        Revisit revisit = revisitService.getByFaultId(fault.getId());
        Evaluation evaluation = evaluationService.getByFaultId(fault.getId());

        translate.setAirConditioner(airConditioner);
        translate.setReportUser(userResponse);
        translate.setAllocation(allocation);
        translate.setRepair(repair);
        translate.setRevisit(revisit);
        translate.setEvaluation(evaluation);
        return translate;
    }

    @GetMapping("/air-conditioner-id/{airConditionerId}")
    public List<FaultTableResponse> listByAirConditionerId(@PathVariable Long airConditionerId) {
        List<Fault> faults = faultService.listByAirConditionerId(airConditionerId);
        List<FaultTableResponse> result =
                faults.stream().map(this::fault2FaultTableResponse).collect(Collectors.toList());
        return result;
    }

    /**
     * 根据用户角色查询故障信息 （分页）
     *
     * @param user  当前登录用户
     * @param page  页码，从 1 开始
     * @param limit 每页行数
     * @return 查询到的故障信息
     */
    @GetMapping("/page")
    public PageResponse<FaultTableResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user, @RequestParam int page,
            @RequestParam int limit) {
        // 将前端page参数与查询page参数对应
        page -= 1;
        if (page <= 0 && limit <= 0) {
            List<FaultTableResponse> data = listByUserRole(user);
            return PageResponse.of(data.size(), data);
        }
        Page<Fault> result;
        if (RoleEnum.REPAIRMAN.equals(user.getRole()) || RoleEnum.CUSTOM_SERVICE.equals(user.getRole())) {
            result = faultService.listAll(page, limit);
        } else {
            result = faultService.listAllByReportUserId(user.getId(), page, limit);
        }
        return PageResponse.of(result, this::fault2FaultTableResponse);
    }

    /**
     * 根据条件查询 故障信息 （不分页）
     *
     * @param user    当前登录用户
     * @param request 查询条件
     * @return 查询到的 故障信息
     */
    @GetMapping("/search")
    public List<FaultTableResponse> listByUserRole(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user,
            @ModelAttribute FaultSearchRequest request) throws InstantiationException, IllegalAccessException {

        Fault probe = EntityTranslatorUtils.translate(request, Fault.class);
        if (RoleEnum.ORDINARY.equals(user.getRole())) {
            probe.setReportUserId(user.getId());
        }

        List<Fault> list = faultService.listAll(Example.of(probe));
        List<FaultTableResponse> result =
                list.stream().map(this::fault2FaultTableResponse).collect(Collectors.toList());

        return result;
    }

    @GetMapping("/state/{state}")
    public List<FaultAllocationTableResponse> listByState(@PathVariable FaultStateEnum state) {
        List<Fault> list = faultService.listAllByState(state);
        List<FaultAllocationTableResponse> result =
                list.stream().map(this::fault2FaultAllocationTableResponse).collect(Collectors.toList());
        return result;
    }

    private FaultAllocationTableResponse fault2FaultAllocationTableResponse(Fault fault) {
        FaultAllocationTableResponse translate = new FaultAllocationTableResponse();
        try {
            translate = EntityTranslatorUtils.translate(fault, FaultAllocationTableResponse.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        AirConditioner airConditioner = airConditionerService.getById(fault.getAirConditionerId());
        translate.setType(fault.getType().getMessage());
        translate.setEquipmentId(airConditioner.getEquipmentId());
        return translate;
    }

    private FaultRepairTableResponse fault2FaultRepairTableResponse(Fault fault) {
        FaultRepairTableResponse translate = new FaultRepairTableResponse();
        try {
            translate = EntityTranslatorUtils.translate(fault, FaultRepairTableResponse.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        AirConditioner airConditioner = airConditionerService.getById(fault.getAirConditionerId());
        Allocation allocation = allocationService.getByFaultId(fault.getId());
        UserResponse userResponse = userController.get(allocation.getAllocationUserId());
        translate.setAllocationUser(userResponse.getUsername());
        translate.setType(fault.getType().getMessage());
        translate.setEquipmentId(airConditioner.getEquipmentId());
        return translate;
    }

    @GetMapping("/allocated")
    public List<FaultRepairTableResponse> listAllocatedFault(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {
        List<Allocation> allocations = allocationService.listByRepairUserId(user.getId());
        List<Fault> faults = new ArrayList<>(allocations.size());
        for (Allocation allocation : allocations) {
            Fault fault = faultService.getById(allocation.getFaultId());
            faults.add(fault);
        }
        List<FaultRepairTableResponse> result = faults.stream()
                .filter(fault -> FaultStateEnum.ALLOCATED.equals(fault.getState()))
                .map(this::fault2FaultRepairTableResponse).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/repairing")
    public List<FaultRepairTableResponse> listRepairingFault(
            @SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {
        List<Allocation> allocations = allocationService.listByRepairUserId(user.getId());
        List<Fault> faults = new ArrayList<>(allocations.size());
        for (Allocation allocation : allocations) {
            Fault fault = faultService.getById(allocation.getFaultId());
            faults.add(fault);
        }
        List<FaultRepairTableResponse> result = faults.stream()
                .filter(fault -> FaultStateEnum.REPAIRING.equals(fault.getState()))
                .map(this::fault2FaultRepairTableResponse).collect(Collectors.toList());
        return result;
    }

    @GetMapping("/repaired")
    public List<FaultRepairedTableResponse> listRepairedFault() {
        List<Repair> repairs = repairService.listAll();
        List<Fault> faults = new ArrayList<>(repairs.size());
        for (Repair repair : repairs) {
            Fault fault = faultService.getById(repair.getFaultId());
            faults.add(fault);
        }
        List<FaultRepairedTableResponse> result = faults.stream()
                .filter(fault -> FaultStateEnum.REPAIRED.equals(fault.getState()))
                .map(this::fault2FaultRepairedTableResponse).collect(Collectors.toList());
        return result;
    }

    private FaultRepairedTableResponse fault2FaultRepairedTableResponse(Fault fault) {
        FaultRepairedTableResponse translate = new FaultRepairedTableResponse();
        try {
            translate = EntityTranslatorUtils.translate(fault, FaultRepairedTableResponse.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        AirConditioner airConditioner = airConditionerService.getById(fault.getAirConditionerId());
        Allocation allocation = allocationService.getByFaultId(fault.getId());
        UserResponse userResponse = userController.get(allocation.getAllocationUserId());
        translate.setAllocationUser(userResponse.getUsername());

        userResponse = userController.get(allocation.getRepairUserId());
        translate.setRepairUser(userResponse.getUsername());

        translate.setType(fault.getType().getMessage());
        translate.setEquipmentId(airConditioner.getEquipmentId());
        return translate;
    }

    @GetMapping("/revisited")
    public List<FaultRevisitTableResponse> listRevisitFault(@SessionAttribute(SessionConsts.LOGIN_USER_KEY) User user) {

        List<Revisit> revisits = revisitService.listAll();
        List<Fault> faults = new ArrayList<>(revisits.size());
        for (Revisit revisit : revisits) {
            Fault fault = faultService.getById(revisit.getFaultId());
            faults.add(fault);
        }
        List<FaultRevisitTableResponse> result = faults.stream()
                .filter(fault -> (FaultStateEnum.REVISITED.equals(fault.getState()) &&
                        (user.getId().equals(fault.getReportUserId()))))
                .map(this::fault2FaultRevisitTableResponse).collect(Collectors.toList());
        return result;
    }

    private FaultRevisitTableResponse fault2FaultRevisitTableResponse(Fault fault) {
        FaultRevisitTableResponse translate = new FaultRevisitTableResponse();
        try {
            translate = EntityTranslatorUtils.translate(fault, FaultRevisitTableResponse.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        AirConditioner airConditioner = airConditionerService.getById(fault.getAirConditionerId());
        Allocation allocation = allocationService.getByFaultId(fault.getId());
        UserResponse userResponse = userController.get(allocation.getAllocationUserId());
        translate.setAllocationUser(userResponse.getUsername());

        userResponse = userController.get(allocation.getRepairUserId());
        translate.setRepairUser(userResponse.getUsername());

        Revisit revisit = revisitService.getByFaultId(fault.getId());
        userResponse = userController.get(revisit.getRevisitUserId());
        translate.setRevisitUser(userResponse.getUsername());

        translate.setType(fault.getType().getMessage());
        translate.setEquipmentId(airConditioner.getEquipmentId());
        return translate;
    }

}
