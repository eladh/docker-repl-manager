import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const subscribers = [];
//todo - load server config from server config request
const stompClient = Stomp.over(new SockJS("http://localhost:3000/repl-websocket"));

export const subscribe = (event, cb) => {
    subscribers.push({event: event, cb: cb});
}

export const initWebSocket = () => {
    stompClient.connect({},
        () => {
            console.log(stompClient);
            subscribers.forEach(subscriber => {
                stompClient.subscribe(subscriber.event, subscriber.cb);
            })
        },
        () => {
            this.connected = false;
        }
    );
    return stompClient
}

export const getStompClient = () => {
    return stompClient;
}


