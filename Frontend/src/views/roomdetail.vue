<template>
  <div class="container" v-cloak>
    <div>
      <h2>{{roomName}}</h2>
    </div>
    <div class="input-group">
      <div class="input-group-prepend">
        <label class="input-group-text">내용</label>
      </div>
      <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage()" />
      <div class="input-group-append">
        <button class="btn btn-primary" type="button" @click="sendMessage()">보내기</button>
      </div>
    </div>
    <ul class="list-group">
      <li class="list-group-item" v-for="message in messages" :key="message.message">
        <a>{{message.sender}} - {{message.message}}</a>
      </li>
    </ul>
    <div></div>
  </div>
</template>

<script>
import http from "../http-common";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

var sock = new SockJS("http://13.125.114.122:8080/api");
var ws = Stomp.over(sock);
var reconnect = 0;
export default {
  name: "roomdetail",
  props: {
    msg: String
  },
  data() {
    return {
      roomId: "",
      roomName: '',
      sender: "",
      message: "",
      messages: [],
    };
  },
  created() {
    this.roomId = localStorage.getItem("wschat.roomId");
    this.roomName = localStorage.getItem("wschat.roomName");
    this.sender = localStorage.getItem("wschat.sender");
    this.findRoom();
    this.connect();
  },
  methods: {
    findRoom: function() {
      http.get("/room/" + this.roomId).then(response => {
        this.room = response.data;
        this.connect();
      });
    },
    sendMessage: function() {
      ws.send(
        "/pub/chat/message",
        JSON.stringify({
          type: "TALK",
          roomId: this.roomId,
          sender: this.sender,
          message: this.message
        }),
        {}
      );
      this.message = "";
    },
    recvMessage: function(recv) {
      this.userCount=recv.userCount
      this.messages.unshift({
        type: recv.type,
        roomId: recv.roomId,
        sender: recv.type == "ENTER" ? "[알림]" : recv.sender,
        message: recv.message,
       
      });
    },
    connect() {
      let this_comp = this;
      ws.connect(
        {},
        function(frame) {
          ws.subscribe("/sub/chat/room/" + this_comp.roomId, function(message) {
            var recv = JSON.parse(message.body);
            this_comp.recvMessage(recv);
            console.log("recv:  "+recv)
          });
          ws.send(
            "/pub/chat/message",
            JSON.stringify({
              type: "ENTER",
              roomId: this_comp.roomId,
              sender: this_comp.sender,
            }),
            {}
          );
          console.log(frame.body);
        },
        function() {
          if (reconnect++ <= 5) {
            setTimeout(function() {
              console.log("connection reconnect");
              sock = new SockJS("http://13.125.114.122:8080/api");
              ws = Stomp.over(sock);
              this_comp.connect();
            }, 10 * 1000);
          }
        }
      );
    }
  }
};

</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}

[v-cloak] {
  display: none;
}
</style>
