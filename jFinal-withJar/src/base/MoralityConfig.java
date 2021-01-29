package base;

import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import controller.ExportController;
import controller.OrgController;
import controller.TestController;
import controller.UserController;
import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.template.Engine;
import pojo.Hide;
import pojo.Org;
import pojo.User;


/**
 * @author Administrator
 */
public class MoralityConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        PropKit.use("config.txt");
        me.setDevMode(PropKit.getBoolean("devMode",false));
        me.setViewType(ViewType.JSP);
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/user", UserController.class);
        me.add("/test", TestController.class);
        me.add("/org", OrgController.class);
        me.add("/export", ExportController.class);
    }

    @Override
    public void configEngine(Engine me) {


    }
    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin c3p0Plugin = createC3p0Plugin();
        me.add(c3p0Plugin);

        ActiveRecordPlugin arp=new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);

        // 配置属性名(字段名)大小写不敏感容器工厂
        arp.setContainerFactory(new CaseInsensitiveContainerFactory());

        //关系映射model
        arp.addMapping("user", User.class);
        arp.addMapping("org", Org.class);
        arp.addMapping("hide", Hide.class);

        // 所有配置在 MappingKit 中搞定
        //_MappingKit.mapping(arp);

        RedisPlugin userRedis=new RedisPlugin("user","localhost");
        me.add(userRedis);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }

    public static C3p0Plugin createC3p0Plugin(){
        return new C3p0Plugin(PropKit.get("jdbcUrl"),PropKit.get("user"),PropKit.get("password").trim());
    }
}
