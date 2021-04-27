import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(item) {
    return HTTP.put(BASE_URL + "/items/" + item.id, item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(id) {
    return HTTP.delete(BASE_URL + "/items/" + id, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  sell(item) {
    return HTTP.patch(BASE_URL + "/items/itemsemp/", item, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  searchBook(string) {
    return HTTP.get(BASE_URL + "/items/search-book/" + string, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
    exportReport(type) {
        return HTTP.get(BASE_URL + "/items/export/" + type, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    }
};
