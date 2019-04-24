package fic.writer.domain.service.impl;

import fic.writer.domain.entity.auth.OauthUser;
import fic.writer.domain.repository.OauthUserRepository;
import fic.writer.domain.service.OauthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OauthUserServiceImpl implements OauthUserService {
    @Autowired
    private OauthUserRepository oauthUserRepository;

    @Override
    public List<OauthUser> findAll() {
        return oauthUserRepository.findAll();
    }

    @Override
    public Page<OauthUser> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<OauthUser> findById(Long id) {
        return oauthUserRepository.findById(id);
    }

    @Override
    public OauthUser save(OauthUser oauthUser) {
        return oauthUserRepository.save(oauthUser);
    }

    @Override
    public void delete(OauthUser oauthUser) {
        oauthUserRepository.delete(oauthUser);
    }

    @Override
    public void deleteById(Long id) {
        oauthUserRepository.deleteById(id);
    }
}
