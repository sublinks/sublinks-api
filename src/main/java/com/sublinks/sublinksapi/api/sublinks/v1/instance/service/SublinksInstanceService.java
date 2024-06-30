package com.sublinks.sublinksapi.api.sublinks.v1.instance.service;

import com.sublinks.sublinksapi.api.sublinks.v1.instance.models.IndexInstance;
import com.sublinks.sublinksapi.api.sublinks.v1.instance.models.InstanceAggregateResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.instance.models.InstanceConfigResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.instance.models.InstanceResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.instance.models.UpdateInstanceConfig;
import com.sublinks.sublinksapi.instance.entities.InstanceConfig;
import com.sublinks.sublinksapi.instance.repositories.InstanceAggregateRepository;
import com.sublinks.sublinksapi.instance.repositories.InstanceConfigRepository;
import com.sublinks.sublinksapi.instance.repositories.InstanceRepository;
import com.sublinks.sublinksapi.instance.services.InstanceConfigService;
import com.sublinks.sublinksapi.instance.services.InstanceService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class SublinksInstanceService {

  private final InstanceRepository instanceRepository;
  private final InstanceConfigRepository instanceConfigRepository;
  private final InstanceAggregateRepository instanceAggregateRepository;
  private final InstanceService instanceService;
  private final InstanceConfigService instanceConfigService;
  private final ConversionService conversionService;

  public List<InstanceResponse> index(final IndexInstance indexInstance) {

    if (indexInstance.search() == null) {
      return instanceRepository.findAll(
              PageRequest.of(indexInstance.page(), indexInstance.perPage()))
          .stream()
          .map(instance -> conversionService.convert(instance, InstanceResponse.class))
          .collect(Collectors.toList());
    }

    return instanceRepository.findInstancesByDomainOrDescriptionOrSidebar(indexInstance.search(),
            PageRequest.of(indexInstance.page(), indexInstance.perPage()))
        .stream()
        .map(instance -> conversionService.convert(instance, InstanceResponse.class))
        .collect(Collectors.toList());
  }

  public InstanceResponse show(final String key) {

    return conversionService.convert(instanceRepository.findInstanceByDomain(key),
        InstanceResponse.class);
  }

  public InstanceAggregateResponse showAggregate(final String key) {

    return conversionService.convert(instanceAggregateRepository.findByInstance_Domain(key),
        InstanceAggregateResponse.class);
  }

  public InstanceConfigResponse updateConfig(final String key,
      final UpdateInstanceConfig updateInstanceConfigForm)
  {

    final InstanceConfig config = instanceConfigRepository.findByInstance_Domain(key)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "instance_not_found"));

    if (updateInstanceConfigForm.nameMaxLength() != null) {
      config.setActorNameMaxLength(Math.max(updateInstanceConfigForm.nameMaxLength(), 0));
    }

    instanceConfigService.updateInstanceConfig(config);
    return conversionService.convert(config, InstanceConfigResponse.class);
  }
}
