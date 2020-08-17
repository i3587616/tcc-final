package cn.linc.tccfinal.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;

import cn.linc.tccfinal.core.transaction.BsActor;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoPool;

import org.objenesis.strategy.StdInstantiatorStrategy;

public class KryoUtil {
	
	private KryoPool kryoPool;
	
	private static volatile WeakReference<KryoUtil> util = new WeakReference<KryoUtil>( new KryoUtil());
	
	private KryoUtil(){
		kryoPool = newKryoPool();
	}
	
	public static KryoUtil getKryoUtil(){
		KryoUtil kryoUtil = util.get();//保证util.get()在此方法期间不为被回收为空
		
		if(kryoUtil == null){//kryoUtil强引用前就是空
			synchronized(KryoUtil.class){
				if(util.get() == null){
					kryoUtil = new KryoUtil();
					util = new WeakReference<KryoUtil>( kryoUtil);
				}
			}
		}
		return kryoUtil;
	}
	
	public KryoPool newKryoPool() {
        return new KryoPool.Builder(() -> {
            final Kryo kryo = new Kryo();
            kryo.setReferences(false);
            kryo.setRegistrationRequired(false);
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(
                    new StdInstantiatorStrategy()));
            return kryo;
        }).softReferences().build();
    }

    public byte[] serialize(final List<BsActor> object) {

        return kryoPool.run(new KryoCallback<byte[]>() {
            public byte[] execute(Kryo kryo) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Output output = new Output(byteArrayOutputStream);
                kryo.writeClassAndObject(output, object);
                output.flush();
                return byteArrayOutputStream.toByteArray();
            }
        });
    }
    
    public List<BsActor> deserialize(final byte[] bytes) {
        return kryoPool.run(new KryoCallback<List<BsActor>>() {
            public List<BsActor> execute(Kryo kryo) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                Input input = new Input(byteArrayInputStream);
                return (List<BsActor>) kryo.readClassAndObject(input);
            }
        });
    }

}
