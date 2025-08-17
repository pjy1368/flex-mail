package com.jayon.flexmail.infrastructure.repository;

import com.jayon.flexmail.domain.exception.BusinessException;
import com.jayon.flexmail.domain.mail.TempMail;
import com.jayon.flexmail.domain.mail.TempMailRepository;
import com.jayon.flexmail.infrastructure.entity.TempMailEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpringDataRedisTempMailRepository implements TempMailRepository {

    private final RedisTempMailRepository redisTempMailRepository;

    @Override
    public TempMail save(TempMail tempMail) {
        TempMailEntity entity = TempMailEntity.from(tempMail);
        TempMailEntity saved = redisTempMailRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<TempMail> findById(String id) {
        return redisTempMailRepository.findById(id)
                .map(TempMailEntity::toDomain);
    }

    @Override
    public TempMail fetchById(String id) {
        return findById(id)
                .orElseThrow(() -> BusinessException.notFound("임시 메일을 찾을 수 없습니다. ID: " + id));
    }
}

@Repository
interface RedisTempMailRepository extends CrudRepository<TempMailEntity, String> {
}
