package com.dennis.spring_data.repository;

import com.dennis.spring_data.model.DoctorRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorRedisRepo {
    public static final String HASH_KEY = "Product";


    @Autowired
    private RedisTemplate template;

    public DoctorRedis save(DoctorRedis doctorRedis){
        template.opsForHash().put(HASH_KEY,doctorRedis.getId(),doctorRedis);
        return doctorRedis;
    }

    public List<DoctorRedis> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }

    public DoctorRedis findDoctorById(int id){
        return (DoctorRedis) template.opsForHash().get(HASH_KEY,id);
    }


    public String deleteDoctor(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "doctor removed !!";
    }
}
