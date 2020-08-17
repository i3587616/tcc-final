package cn.linc.tccfinal.sure;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.linc.tccfinal.consts.TccStatus;
import cn.linc.tccfinal.core.dao.TccDao;
import cn.linc.tccfinal.core.transaction.BsActor;

@Component
public class SureDoJob implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware {

	@Autowired
	private TccDao tccDao;

	private ApplicationContext applicationContext;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		// 容器初始化完成执行方法

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				List<Map<String, Object>> undoUcc = tccDao.undoUcc();

				if (undoUcc == null || undoUcc.size() == 0) {
					return;
				}

				for (Map<String, Object> ucc : undoUcc) {
					String tcc_id = ucc.get("tcc_id").toString();
					String status_s = ucc.get("status").toString();
					Integer status = Integer.valueOf(status_s);
					try {
						List<BsActor> bsActors = tccDao.findBsActors(new Object[] {ucc.get("ucc_sign"), ucc.get("tcc_ver") });
						for (BsActor bs : bsActors) {
							bs.setTccId(tcc_id);
							bs.setTarget(applicationContext.getBean(bs.getInterfaceName()));
							if (TccStatus.CONFIRM.getStatus() == status) {
								bs.confirm();
							} else {
								bs.cancel();
							}
						}

						// 都 执行完然后删除记录
						tccDao.uccDelete(tcc_id);

					} catch (Throwable e) {
						e.printStackTrace();// 这里需要修改，导入日志系统或传输到MQ队列
						// 理论上confirm和cancel是一定会执行通过的，不会报错
					}

				}
			}

		}, 0, 6000);// 无延时，每隔6s运行一次

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
