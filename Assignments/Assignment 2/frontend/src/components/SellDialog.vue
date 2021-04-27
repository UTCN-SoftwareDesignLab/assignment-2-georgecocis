<template>
    <v-dialog
            transition="dialog-bottom-transition"
            max-width="600"
            :value="opened"
    >
        <template>
            <v-card>
                <v-toolbar color="primary" dark>
                    {{"Sell Item"}}
                </v-toolbar>
                <v-form>
                </v-form>
                <v-card-actions>
                </v-card-actions>
            </v-card>
            <v-btn @click="sellBook">Sell Book</v-btn>
        </template>
    </v-dialog>
</template>

<script>
    import api from "../api";

    export default {
        name: "SellDialog",
        props: {
            item: Object,
            opened: Boolean,
        },
        methods: {
            sellBook(){
                api.items.sell(this.item)
                    .then(() => this.$emit("refresh"));
            }
        },
        computed: {
            isNew: function () {
                return !this.item.id;
            },
        },
    };
</script>

<style scoped></style>