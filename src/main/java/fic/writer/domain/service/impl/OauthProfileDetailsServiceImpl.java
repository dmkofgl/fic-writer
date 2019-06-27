package fic.writer.domain.service.impl;

import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.repository.OauthProfileDetailsRepository;
import fic.writer.domain.service.OauthProfileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OauthProfileDetailsServiceImpl implements OauthProfileDetailsService {
    @Autowired
    private OauthProfileDetailsRepository oauthProfileDetailsRepository;

    @Override
    public List<OauthProfileDetails> findAll() {
        return oauthProfileDetailsRepository.findAll();
    }

    @Override
    public Page<OauthProfileDetails> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<OauthProfileDetails> findById(Long id) {
        return oauthProfileDetailsRepository.findById(id);
    }

    @Override
    public OauthProfileDetails save(OauthProfileDetails oauthProfileDetails) {
        return oauthProfileDetailsRepository.save(oauthProfileDetails);
    }

    @Override
    public void delete(OauthProfileDetails oauthProfileDetails) {
        oauthProfileDetailsRepository.delete(oauthProfileDetails);
    }

    @Override
    public void deleteById(Long id) {
        oauthProfileDetailsRepository.deleteById(id);
    }
}
