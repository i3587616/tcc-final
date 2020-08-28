# tcc-final

#### 介绍
tcc分布式事务解决方案

通常一个请求到达网关，经由网关到APP中心，APP中心调用服务完成业务流程。
这里是在APP中心里增加了ucc层，使用ucc调用服务。一个ucc代表一个完整的业务流程。

一个业务分为try、confirm、cancel方法。try执行通过，confirm、cancel必须保证能正常执行。
每一个完整的业务流程都要求有一个全分布式系统唯一的tcc_id。


#### 软件架构

sql  
    tcc_db.sql 核心sql，记录ucc,bs相关信息；
    tcc_acount_points.sql、tcc_acount_pay.sql应用例子sql。应用例子是为了说明tcc-final的使用和测试。

tcc-final-core
    tcc-final核心，包含切面的定义等。

tcc-final-consts
    tcc-final常量定义。

tcc-springcloud-starter
    tcc-final结合springcloud eureka使用时须导入此包。

tcc-final-cloud-eureka
    tcc-final结合springcloud使用例子
    tcc-cloud-pay-api 账户支付接口
    tcc-cloud-pay 账户支付业务
    tcc-cloud-points-api 账户积分接口
    tcc-cloud-points账户积分业务
    tcc-cloud-server eureka服务中心
    tcc-cloud-web-app app应用中心，在这里使用ucc调用业务
    tcc-final-sure 定时6秒查找因各种异常原因超过3分钟未正常执行完的ucc，然后使之正常执行完


#### 安装教程

1.  暂时只适合springcloud eureka使用

2.  在应用中心，引入tcc-final-core包，在定义ucc接口和实现类。在ucc接口有分布式事务的方法上加@TccTransaction(ucc = true)
    在ucc实现类里调用各业务接口完成业务流程。在需要参与分布式事务的接口try方法上加@TccTransaction
    如果一个ucc发生了变化，需加入ver参数使生成新的ucc流程定义。eg.@TccTransaction(ucc = true,ver = 1)//版本号变更会生成新的tcc_bs
    bs的confirm、cancel方法名字默认为try方法名首字母大写在在前面加上confirm、cancel；如果bs自定义了不一样的confirm、cancel方法名，需    
    在注解上加上confirm,cancel方法名参数
    
    @TccTransaction只在接口方法上使用。只用于ucc和bs接口。

3. tcc-final-core当ucc执完try方法正常结束则执行各业务confirm，如try过程中异常则执行各业务cancel。

4.  tcc-final-sure 定时6秒查找因各种异常原因超过3分钟未正常执行完的ucc，然后使之正常执行完

#### 使用说明

    一个完整的分布式业务流程就是一个ucc。其try尝试执行，如try能通过，则confirm一定能正常执行通过。如果try无法执行通过，则cancel一定
    能正常执行通过。保证的是最终一致性。try是尝试执行，并记录业务信息，confirm是确认，cancel
    是回退到最初，confirm、cancel都是通过try记录的业务信息来执行的。
     
    这里业务服务示例应该用mq或其它做一个幂等。
    eg。一个简单的业务，账户支付、账户加积分，最终一起成功或失败。 支付10元，加100积分。则try方法为支付账户里减10元，积分账户不动，并在 
    记录表里记下这次业务数据；  
    假设先执行的是积分服务；
    try都执行通过，那么confirm方法支付账户不动，积分账户里加上100积分，完成业务流程；   
    支付try执行不通过，则执行cancel，积分账户不动，支付账户里加上业务数据里记录的10元钱，完成业务流程。
    
    tcc-final-sure 定时6秒查找因各种异常原因超过3分钟未正常执行完的ucc，比如应用中心突然挂机了，然后调用bs执行完业务流程


#### 更新后续
1.数据库变更为多库，一表变为多表，增加并发量
2.增加其它项目子包，使之能结合dubbo等其它框架使用
3.更改一些方式，使之异步执行confirm和cancel各业务
4.增加其它持久化方法，使之可以不一定非得用数据库来持久分布式信息
5.增加一个外部记录类，代替printStackTrace,使异常信息直接输入到业务系统指定地方。默认实现为在系统控制台打印。
6.分布式事务数据中心。分布式事务数据不直接物理删除，移到history表中。展现管理各分布式事务数据。
7.业务示例用mq队列或其它方式做一个幂等控制。

