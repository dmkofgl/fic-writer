package fic.writer.domain.service.impl;

import fic.writer.domain.entity.auth.OauthUserDetails;
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
    public List<OauthUserDetails> findAll() {
        return oauthUserRepository.findAll();
    }

    @Override
    public Page<OauthUserDetails> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<OauthUserDetails> findById(Long id) {
        return oauthUserRepository.findById(id);
    }

    @Override
    public OauthUserDetails save(OauthUserDetails oauthUserDetails) {
        return oauthUserRepository.save(oauthUserDetails);
    }

    @Override
    public void delete(OauthUserDetails oauthUserDetails) {
        oauthUserRepository.delete(oauthUserDetails);
    }

    @Override
    public void deleteById(Long id) {
        oauthUserRepository.deleteById(id);
    }
}
