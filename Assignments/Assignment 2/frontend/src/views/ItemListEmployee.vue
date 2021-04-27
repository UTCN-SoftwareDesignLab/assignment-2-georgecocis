<template>
    <v-card>
        <v-card-title>
            Employee panel: Items
            <v-spacer></v-spacer>
            <v-text-field
                    v-model="search"
                    append-icon="mdi-magnify"
                    label="Search"
                    single-line
                    hide-details
            ></v-text-field>
            <v-btn @click="searchBook">Search Book</v-btn>
        </v-card-title>
        <v-data-table
                :headers="headers"
                :items="items"
                @click:row="editItem"
        ></v-data-table>
        <v-btn @click="refreshList">Refresh</v-btn>
        <SellDialog
                :opened="dialogVisible"
                :item="selectedItem"
                @refresh="refreshList"
        ></SellDialog>
    </v-card>
</template>

<script>
    import api from "../api";
    import SellDialog from "../components/SellDialog";

    export default {
        name: "ItemListEmployee",
        components: {SellDialog},
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
            async searchBook(){
                this.items = await api.items.searchBook(this.search);
            },
            async refreshList() {
                this.dialogVisible = false;
                this.selectedItem = {};
                this.items = await api.items.allItems();
            },
        },
        created() {
            this.refreshList();
        },
    };
</script>

<style scoped></style>
