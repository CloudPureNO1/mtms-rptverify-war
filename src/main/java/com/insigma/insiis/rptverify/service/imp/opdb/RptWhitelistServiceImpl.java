package com.insigma.insiis.rptverify.service.imp.opdb;

import com.insigma.insiis.rptverify.dao.RptVerifyMapper;
import com.insigma.insiis.rptverify.dao.RptWhitelistMapper;
import com.insigma.insiis.rptverify.model.RptVerify;
import com.insigma.insiis.rptverify.model.RptWhitelist;
import com.insigma.insiis.rptverify.service.opdb.RptVerifyService;
import com.insigma.insiis.rptverify.service.opdb.RptWhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/9 14:13
 * @since 1.0.0
 */
@Slf4j
@Service
public class RptWhitelistServiceImpl implements RptWhitelistService {
    @Resource
    private RptWhitelistMapper rptWhitelistMapper;

    @Override
    public int add(RptWhitelist rptWhitelist) {
        return rptWhitelistMapper.insertByJavaUUID(rptWhitelist);
    }

    @Override
    public RptWhitelist findByID(String whiteId) {
        return rptWhitelistMapper.selectByPrimaryKey(whiteId);
    }

    @Override
    public List<RptWhitelist> findAll(String whiteFlag) {
        return rptWhitelistMapper.selectAll(whiteFlag);
    }

    @Override
    public boolean checkWhiteList(String ip) {
        try{
            List<RptWhitelist>list=findAll("1");
            if(list!=null&&list.size()>0){
                return list.stream().anyMatch(item->item.getWhiteIp().equals(ip));
            }
            return false;
        }catch (Exception e){
            log.info("校验数据库中的白名单发生异常：{}",e.getMessage(),e);
            return false;
        }
    }
}
