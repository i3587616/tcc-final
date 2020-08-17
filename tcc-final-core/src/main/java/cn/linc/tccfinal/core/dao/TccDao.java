package cn.linc.tccfinal.core.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.linc.tccfinal.core.transaction.BsActor;
import cn.linc.tccfinal.core.util.KryoUtil;

@Component
public class TccDao {

    @Autowired
	private JdbcTemplate jdbcTemplate;
    
    private final String uccInsert  = "insert into tcc_ucc(ucc_sign,tcc_id,status,tcc_ver) values (?,?,0,?)";
    
    private final String uccStatusUpdate  = "update tcc_ucc set status = ? where tcc_id = ?";
    
    private final String uccDelete  = "delete from tcc_ucc where tcc_id = ?";
    
    private final String bsInsert = "insert into tcc_bs(ucc_sign,bs_actors,ver) values (?,?,?)";
    
    private final String hasUcc = "select count(1) from tcc_bs where ucc_sign = ? and ver =?";
    
    private final String undoUcc = "select t.tcc_id,t.`status`,t.ucc_sign,t.tcc_ver from tcc_ucc t where t.update_time < DATE_SUB(NOW(),INTERVAL 3 MINUTE) limit 0,1000";
    
    private final String findBsActors = "select bs_actors from tcc_bs where ucc_sign = ? and ver =? limit 1";
    
    @Transactional
    public void uccInsert(String uccSign,String tccId,int ver){
    	
    	this.jdbcTemplate.update(uccInsert, new Object[]{uccSign,tccId,ver});
    }
    
    @Transactional
    public void uccStatusUpdate(Integer status,String tccId){
    	
    	this.jdbcTemplate.update(uccStatusUpdate, new Object[]{status,tccId});
    }
    
    @Transactional
    public void uccDelete(String tccId){
    	
    	this.jdbcTemplate.update(uccDelete, new Object[]{tccId});
    }
    
    @Transactional
    public void bsInsert(String uccSign,byte[] bsActors,int ver){
    	
    	this.jdbcTemplate.update(bsInsert, new Object[]{uccSign,bsActors,ver});
    }
    
    @Transactional(readOnly = true)
    public boolean hasUcc(String uccSign,int ver){
    	
    	int num = this.jdbcTemplate.queryForObject(hasUcc, new Object[]{uccSign,ver},Integer.class);
    	if(num>0){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 查找出三分钟还未执行完的任务，不超过1000条（这种数据应该很少)
     */
    @Transactional(readOnly = true)
    public List<Map<String,Object>> undoUcc(){
    	
    	return this.jdbcTemplate.queryForList(undoUcc);

    }
    
    @Transactional(readOnly = true)
    public List<BsActor> findBsActors(Object[] args){
    	
    	byte[] rs = this.jdbcTemplate.queryForObject(findBsActors, args,byte[].class);
    	
    	return KryoUtil.getKryoUtil().deserialize(rs);
    	
    }
}
