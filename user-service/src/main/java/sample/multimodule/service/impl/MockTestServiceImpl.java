package sample.multimodule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.multimodule.domain.UserEntity;
import sample.multimodule.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service("mockTestService")
public class MockTestServiceImpl {

    @Autowired
    UserRepository userRepository;

    public static String staticMethod(String args){
        return args;
    }

    public String callStaticMethod(String args){
        try {
            String v = MockTestServiceImpl.staticMethod("i100121");
            System.out.println("callStaticMethod Test : " + v);
        }catch (Exception e){
            System.out.println("callStaticMethod Test : Exception");
        }
        return args;
    }

    private String privateMethod(String args){
        //UserEntity e = userRepository.findById(1L).get();
        return args;
    }

    public String callPrivateMethod(String args){
        try {
            String v = privateMethod("i100121");
            System.out.println("callPrivateMethod Method Test : " + v);
        }catch (Exception e){
            System.out.println("callPrivateMethod Method Test : Exception");
        }
        return args;
    }

    public String newObject(String args){
        try {
            ArrayList<String> l = new ArrayList<>();
            String v = l.get(0);
            System.out.println("New Object Method Test : " + v);
        }catch (Exception e){
            System.out.println("New Object Method Test : Exception");
        }
        return args;
    }

    public String partialMethod(String args){

        return args;
    }
}
