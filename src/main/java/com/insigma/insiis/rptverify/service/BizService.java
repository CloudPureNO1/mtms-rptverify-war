package com.insigma.insiis.rptverify.service;

import com.insigma.insiis.rptverify.Exception.BaseException;
import com.insigma.insiis.rptverify.comm.DataRtn;
import com.insigma.insiis.rptverify.dto.in.Tran10001In;
import com.insigma.insiis.rptverify.dto.in.Tran20001In;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/4 14:01
 * @since 1.0.0
 */
@Validated
public interface BizService {
    /**
     * 下载方法
     * @param tran10001In
     * @return
     * @throws BaseException
     */
    DataRtn serve10001(@Valid Tran10001In tran10001In) throws BaseException;

    /**
     * 验证方法
     * @param tran20001In
     * @return
     * @throws BaseException
     */
    DataRtn serve20001(@Valid Tran20001In tran20001In) throws BaseException;
}
