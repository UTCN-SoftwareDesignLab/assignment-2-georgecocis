import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

  createUser(item) {
    return HTTP.post(BASE_URL + "/users", item, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },

  updateUser(item) {
    return HTTP.patch(BASE_URL + "/users/" + item.id, item, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },

  deleteUser(id) {
    return HTTP.delete(BASE_URL + "/users/" + id, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },

  getUser(id) {
    return HTTP.get(BASE_URL + "/users/" + id, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
};
