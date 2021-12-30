package io.security.basicsecurity.service;

import io.security.basicsecurity.domain.entity.Resources;
import io.security.basicsecurity.repository.ResourcesRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ResourcesService {

  private final ResourcesRepository resourcesRepository;

  @Transactional
  public Resources getResources(long id) {
    return resourcesRepository.findById(id).orElse(new Resources());
  }

  @Transactional
  public List<Resources> getResources() {
    return resourcesRepository.findAll(Sort.by(Sort.Order.asc("orderNum")));
  }

  @Transactional
  public void createResources(Resources resources) {
    resourcesRepository.save(resources);
  }

  @Transactional
  public void deleteResources(long id) {
    resourcesRepository.deleteById(id);
  }
}
