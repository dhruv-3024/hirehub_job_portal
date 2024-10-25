package com.hirehub.utility;

import com.hirehub.entity.Sequence;
import com.hirehub.exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Utilities {
//    chaining seq programmatically
    private static MongoOperations mongoOperation;  //setter injection will be used for static context

    @Autowired
    public void setMongoOperation (MongoOperations mongoOperation){
        Utilities.mongoOperation = mongoOperation;
    }

    public static Long getNextSequence(String key) throws Exception{
        Query query = new Query(Criteria.where("_id").is(key));
        Update update = new Update();
        update.inc("seq",1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        Sequence seq = mongoOperation.findAndModify(query,update,options,Sequence.class);
        if(seq == null){
            throw new Exception("Unable to get sequence id for key : "+key);

        }
        return seq.getSeq();
    }

//    OTP creation using SecureRandom
    public static String generateOTP(){
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i < 6 ; i++){
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
