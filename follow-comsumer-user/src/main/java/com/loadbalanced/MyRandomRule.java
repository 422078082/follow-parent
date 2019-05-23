package com.loadbalanced;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class MyRandomRule extends AbstractLoadBalancerRule {
    private int total=0;

    private int currentTotal=0;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    //@Override
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        } else {
            Server server = null;

            while (server == null) {
                if (Thread.interrupted()) {
                    return null;
                }

                List<Server> upList = lb.getReachableServers();
                List<Server> allList = lb.getAllServers();
                int serverCount = allList.size();
                if (serverCount == 0) {
                    return null;
                }
                if(total<4){
                        server=upList.get(currentTotal);
                        total++;
                }else{
                    total=0;
                    currentTotal++;
                    if(currentTotal>=upList.size()){
                        currentTotal=0;
                    }
                }
              /*  int index = this.rand.nextInt(serverCount);
                server = (Server)upList.get(index);*/
                if (server == null) {
                    Thread.yield();
                } else {
                    if (server.isAlive()) {
                        return server;
                    }

                    server = null;
                    Thread.yield();
                }
            }

            return server;
            // return null;
        }


    }

    public Server choose(Object key) {

        return this.choose(this.getLoadBalancer(), key);
    }
}