<template>
  <v-card>
    <v-card-title>
      Admin panel: Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="userOperations"
    ></v-data-table>
    <v-btn @click="addUser">Add Employee</v-btn>
    <v-btn @click="switchOp">Switch to Items</v-btn>
    <UserDialog
       :opened="dialogVisible"
       :user="selectedUser"
       @refresh="refreshList"
    ></UserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog"
import router from "../router";

export default {
  name: "UserList",
  components: {UserDialog},
  data() {
    return {
      users: [],
      search: "",
      headers: [
         {
           text: "Username",
           align: "start",
           sortable: false,
           value: "name",
         },
        { text: "Id", value: "id"},
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    addUser(){
      this.dialogVisible = true
    },
    userOperations(user){
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.users = await api.users.allUsers();
    },
    switchOp(){
      router.push("/items");
    }
  },
  async created() {
    this.users = await api.users.allUsers();
  },
};
</script>

<style scoped></style>
