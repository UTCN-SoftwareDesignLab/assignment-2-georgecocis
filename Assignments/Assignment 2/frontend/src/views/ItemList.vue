<template>
  <v-card>
    <v-card-title>
      Admin panel: Items
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
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <v-btn @click="addItem">Add Item</v-btn>
    <v-btn @click="generateCSV">Generate CSV</v-btn>
    <v-btn @click="generatePDF">Generate PDF</v-btn>
    <v-btn @click="switchToUsers">Switch to Users</v-btn>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";
import router from "../router";

export default {
  name: "ItemList",
  components: { ItemDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedItem: {},
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    generatePDF(){
      api.items.exportReport("PDF");
    },
    generateCSV(){
      api.items.exportReport("CSV");
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
    switchToUsers(){
      router.push("/users");
    }
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
