<template>
  <div class="container" v-cloak>
    <div class="row">
      <div class="col-md-12">
        <h3>채팅방 리스트</h3>
      </div>
    </div>
    <div class="input-group">
      <div class="input-group-prepend">
        <label class="input-group-text">방제목</label>
      </div>
      <input type="text" class="form-control" v-model="room_name" v-on:keyup.enter="createRoom" />
      <div class="input-group-append">
        <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
      </div>
    </div>
    <ul class="list-group">
      <li
        class="list-group-item list-group-item-action"
        v-for="item in chatrooms"
        v-bind:key="item.roomId"
        v-on:click="enterRoom(item.roomId,item.name)"
      >{{item.name}}</li>
    </ul>
  </div>
</template>

<script>
import http from "../http-common";

export default {
  name: "room",
  props: {
    msg: String
  },
  data() {
    return {
      room_name: "",
      chatrooms: []
    };
  },
  created() {
    this.findAllRoom();
  },
  methods: {
    findAllRoom: function() {
      http
      .get("/rooms").then(response => {
        this.chatrooms = response.data;
      });
    },
    createRoom: function() {
      if ("" === this.room_name) {
        alert("방 제목을 입력해 주십시오.");
        return;
      } else {
        var params = new URLSearchParams();
        params.append("name", this.room_name);
        http
          .post("/room", params)
          .then(response => {
            alert(response.data.name + "방 개설에 성공하였습니다.");
            this.room_name = "";
            this.findAllRoom();                                                                
          })
          .catch(error => {
            alert(error.response + "채팅방 개설에 실패하였습니다.");
          })
          ;
      }
    }, 
    enterRoom: function(roomId, room_name) {
      var sender = prompt("대화명을 입력해 주세요.");
      if (sender != "") {
        localStorage.setItem("wschat.roomId", roomId);
        localStorage.setItem("wschat.room_name", room_name);
        localStorage.setItem("wschat.sender", sender);
        location.href = "/roomdetail/" + roomId;
      }
    } 
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
