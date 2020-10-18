<template>
  <div id="app">
    <div id="starter" v-if="started == false">
      <section>
        <div class="input-group mb-3">
          <input type="text" class="form-control" placeholder="Nom" aria-label="username" v-model="text_username" />
          <div class="input-group-append">
            <button type="button" class="btn btn-info" v-on:click="addUser(text_username)">Ajouter un joueur</button>
          </div>
        </div>
        <div id="user-list" class="list-group overflow-auto">
          <div v-for="user in users" :key="user.username" class="list-group-item">{{ user.username }}</div>
        </div>
      </section>
      <button id="start-btn" type="button" class="btn btn-success" v-on:click="start()">Démarrer</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      text_username: "",
      users: [],
      started: false
    }
  },
  methods: {
    isAlphaNum: (str) => {
      for (const c of str) {
        if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
          return false;
        }
      }
      return true;
    },
    addUser(userArg) {
      if (userArg === "" || this.isAlphaNum(userArg) == false) return;
      for (const user of this.users) if (user.username == userArg) return;
      this.users.push({
        username: userArg,
        score: 0
      });
      this.text_username = "";
    },
    start() {
      if (this.started == true) return;
      if (this.users.length <= 1) return;
      this.started = true;
    }
  }
}
</script>

<style>
#starter {
  margin: 10%;
}

#user-list {
  height: 50vh;
}

#start-btn {
  margin-top: 20px;
}
</style>
