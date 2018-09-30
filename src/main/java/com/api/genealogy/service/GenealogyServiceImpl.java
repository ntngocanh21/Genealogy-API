package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.*;
import com.api.genealogy.repository.GenealogyRepository;
import com.api.genealogy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GenealogyServiceImpl implements GenealogyService  {

    @Autowired
    private GenealogyRepository genealogyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GenealogyResponse getGenealogies() {
        List<GenealogyEntity> genealogyEntities = (List<GenealogyEntity>) genealogyRepository.findAll();
        if (genealogyEntities.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse(404,"No genealogy found");
            GenealogyResponse genealogyResponse = new GenealogyResponse(messageResponse, null);
            return genealogyResponse;
        }
        List<Genealogy> genealogies = parseListGenealogyEntityToListGenealogy(genealogyEntities);
        MessageResponse messageResponse = new MessageResponse(0,"Success");
        GenealogyResponse genealogyResponse = new GenealogyResponse(messageResponse, genealogies);
        return genealogyResponse;
    }

    @Override
    public GenealogyResponse getGenealogiesByUsername(String username) {
        List<GenealogyEntity> genealogyEntities = (List<GenealogyEntity>) genealogyRepository
                .findGenealogyEntitiesByUserEntity_Username(username);
        if (genealogyEntities.isEmpty()) {
            MessageResponse messageResponse = new MessageResponse(404,"No genealogy found");
            GenealogyResponse genealogyResponse = new GenealogyResponse(messageResponse, null);
            return genealogyResponse;
        }
        List<Genealogy> genealogies = parseListGenealogyEntityToListGenealogy(genealogyEntities);
        MessageResponse messageResponse = new MessageResponse(0,"Success");
        GenealogyResponse genealogyResponse = new GenealogyResponse(messageResponse, genealogies);
        return genealogyResponse;
    }

    @Override
    public CreateResponse createGenealogy(String username, Genealogy genealogy) {
        CreateResponse createResponse = new CreateResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = new GenealogyEntity();
        genealogyEntity.setName(genealogy.getName());
        genealogyEntity.setHistory(genealogy.getHistory());
        genealogyEntity.setCreatedDate(new Date());
        genealogyEntity.setLastUpdated(new Date());
        genealogyEntity.setUserEntity(userEntity);

        GenealogyEntity newGenealogy = genealogyRepository.save(genealogyEntity);
        createResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        createResponse.setId( newGenealogy.getId());
        return createResponse;
    }

    @Override
    public CodeResponse updateGenealogy(String username, Genealogy genealogy) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = genealogyRepository.findGenealogyEntityById(genealogy.getId());
        if (genealogyEntity != null){
            if(genealogyEntity.getUserEntity().getId() == userEntity.getId()){
                genealogyEntity.setName(genealogy.getName());
                genealogyEntity.setHistory(genealogy.getHistory());
                genealogyEntity.setLastUpdated(new Date());
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
            }
            else {
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.UNAUTHORIZED,"Don't have permission"));
            }
        }
        else {
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"No genealogy found"));
        }
        return codeResponse;
    }

    @Override
    public CodeResponse deleteGenealogy(String username, Integer genealogyId) {
        CodeResponse codeResponse = new CodeResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = genealogyRepository.findGenealogyEntityById(genealogyId);
        if (genealogyEntity == null){
            codeResponse.setError(new MessageResponse(HTTPCodeResponse.OBJECT_NOT_FOUND,"No genealogy found"));
        }
        else {
            if(genealogyEntity.getUserEntity().getId() == userEntity.getId()){
                genealogyRepository.deleteById(genealogyEntity.getId());
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
            }
            else {
                codeResponse.setError(new MessageResponse(HTTPCodeResponse.UNAUTHORIZED,"Don't have permission"));
            }
        }
        return codeResponse;
    }

    private Genealogy parseGenealogyEntityToGenealogy(GenealogyEntity genealogyEntity) {
        Genealogy genealogy = new Genealogy();
        genealogy.setId(genealogyEntity.getId());
        genealogy.setHistory(genealogyEntity.getHistory());
        genealogy.setName(genealogyEntity.getName());
        genealogy.setCreatedDate(genealogyEntity.getCreatedDate());
        genealogy.setLastUpdated(genealogyEntity.getLastUpdated());
        genealogy.setUserEntity(genealogyEntity.getUserEntity());
        return genealogy;
    }

    private List<Genealogy> parseListGenealogyEntityToListGenealogy(List<GenealogyEntity> genealogyEntities) {
        List<Genealogy> genealogies = new ArrayList<>();
        for (GenealogyEntity genealogyEntity : genealogyEntities) {
            Genealogy genealogy = parseGenealogyEntityToGenealogy(genealogyEntity);
            genealogies.add(genealogy);
        }
        return genealogies;
    }


}