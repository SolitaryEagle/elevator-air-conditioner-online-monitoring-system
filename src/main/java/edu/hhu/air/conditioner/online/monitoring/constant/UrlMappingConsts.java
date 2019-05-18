package edu.hhu.air.conditioner.online.monitoring.constant;

/**
 * @author 覃国强
 * @date 2019/5/16 10:13
 */
public interface UrlMappingConsts {

    String PROJECT_BASE_MAPPING_V1 = "/v1/monitoring-system";
    String USER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/users";
    String AIR_CONDITIONER_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/air-conditioners";
    String FAULT_BASE_MAPPING_V1 = PROJECT_BASE_MAPPING_V1 + "/faults";
    String ALLOCATION_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/allocations";
    String REPAIR_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/repairs";
    String REVISIT_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/revisits";
    String EVALUATION_BASE_MAPPING_V1 = FAULT_BASE_MAPPING_V1 + "/evaluations";

}
