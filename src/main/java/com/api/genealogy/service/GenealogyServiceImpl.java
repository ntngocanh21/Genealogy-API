package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.GenealogyEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.*;
import com.api.genealogy.repository.GenealogyRepository;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.response.CodeResponse;
import com.api.genealogy.service.response.GenealogyResponse;
import com.api.genealogy.service.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public GenealogyResponse createGenealogy(String username, Genealogy genealogy) {
        GenealogyResponse genealogyResponse = new GenealogyResponse();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username);
        GenealogyEntity genealogyEntity = new GenealogyEntity();
        genealogyEntity.setName(genealogy.getName());
        genealogyEntity.setHistory(genealogy.getHistory());
        genealogyEntity.setUserEntity(userEntity);

        GenealogyEntity newGenealogy = genealogyRepository.save(genealogyEntity);
        ArrayList<Genealogy> genealogies = new ArrayList<>();
        genealogies.add(parseGenealogyEntityToGenealogy(newGenealogy));
        genealogyResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
        genealogyResponse.setGenealogyList(genealogies);
        return genealogyResponse;
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
                genealogyRepository.save(genealogyEntity);
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
                genealogyRepository.deleteById(genealogyId);
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