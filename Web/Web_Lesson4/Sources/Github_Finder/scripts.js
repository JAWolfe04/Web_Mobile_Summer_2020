$(document).ready(function () {
    let rows = 5, page = 1, query = '', paging_start = 1, paging_size = 5;

    $("#search_button").click(function(){
        query = $("#username").val();
        page = 1;
        paging_start = 1;
        searchUsers();
    });

    function searchUsers(){
        $.get({url: "https://api.github.com/search/users?q=" + query + "&page=" + page + "&per_page=" + rows,
                type: "GET",
                headers: { "Authorization": "token d0c7a207b6b8af793a67ec9c01296f6c14bb6249" }})
            .done(processData)
            .fail(function() { $("#results").text("Failed Request"); });
    };

    function processData(data) {
        $("#results").empty();
        if(data.items.length > 0) {
            let total_users_row = document.createElement("div");
            let total_users_col = document.createElement("div");
            total_users_row.className = "row";
            total_users_col.className = "col total_user_style";
            total_users_col.innerText = data.total_count.toLocaleString() + " users";
            $("#results").append(total_users_col);

            data.items.forEach(function(item, index) {
                let row = document.createElement("div");
                let col1 = document.createElement("div");
                let col2 = document.createElement("div");
                row.className = "row py-4";
                row.style.borderTop = "1px solid #e1e4e8";
                col1.className = "col-1";
                col2.className = "col-11";

                let avatar = document.createElement("img");
                avatar.src = item.avatar_url;
                avatar.width = 50;
                col1.append(avatar);

                let inner_row1 = document.createElement("div");
                let inner_row2 = document.createElement("div");
                let inner_row3 = document.createElement("div");
                inner_row1.className = "row";
                inner_row2.className = "row";
                inner_row3.className = "row";

                let login = document.createElement("a");
                login.href = item.html_url;
                login.innerText = item.login;
                login.className = "usernames";
                inner_row1.append(login);

                $.get({url: "https://api.github.com/users/" + item.login,
                    type: "GET",
                    headers: { "Authorization": "token d0c7a207b6b8af793a67ec9c01296f6c14bb6249" }})
                    .done(function(data){
                        let username = document.createElement("p");
                        username.innerText = "- " + data.name;
                        username.className = "ml-1 usernames";
                        inner_row1.append(username);

                        let id = document.createElement("p");
                        id.innerText = "- " + item.id;
                        username.className = "ml-1 usernames";
                        inner_row1.append(id);

                        let bio = document.createElement("p");
                        bio.className = "user_bio";
                        bio.innerText = data.bio;
                        inner_row2.append(bio);

                        let location = document.createElement("p");
                        location.className = "user_info";
                        location.innerText = data.location;
                        inner_row3.append(location);

                        let blog = document.createElement("p");
                        blog.className = "ml-4 user_info";
                        blog.innerText = data.blog;
                        inner_row3.append(blog);
                    });

                col2.append(inner_row1, inner_row2, inner_row3);
                row.append(col1, col2);

                $("#results").append(row);
            });

            let paging = document.createElement("ul");
            paging.className = "pagination justify-content-center";
            let prev_item = document.createElement("li");
            let prev_link = document.createElement("a");
            prev_item.className = "page-item";
            prev_link.className = "page-link";
            prev_link.href = "#";
            prev_link.innerText = "Previous";
            if(paging_start < 6) {
                prev_item.className = "page-item disabled";
            }
            prev_link.onclick = function(){
                page = paging_start - 1;
                paging_start -= paging_size;
                searchUsers();
            };
            prev_item.append(prev_link);
            paging.append(prev_item);

            for(let i = paging_start; i < paging_start + paging_size; ++i) {
                let page_item = document.createElement("li");
                let page_link = document.createElement("a");
                page_item.className = "page-item";
                page_link.className = "page-link";
                page_link.href = "#";
                page_link.innerText = i.toString();
                if(i === page) {
                    page_item.className = "page-item active";
                }
                else if((i - 1) * rows > data.total_count) {
                    page_item.className = "page-item disabled";
                } else {
                    page_link.onclick = function(){
                        page = i;
                        searchUsers();
                    };
                }
                page_item.append(page_link);
                paging.append(page_item);
            }

            let next_item = document.createElement("li");
            let next_link = document.createElement("a");
            next_item.className = "page-item";
            next_link.className = "page-link";
            next_link.href = "#";
            next_link.innerText = "Next";
            if((paging_start + paging_size - 1) * rows >= data.total_count) {
                next_item.className = "page-item disabled";
            }
            next_link.onclick = function(){
                page = paging_start + paging_size;
                paging_start += paging_size;
                searchUsers();
            };
            next_item.append(next_link);
            paging.append(next_item);

            $("#results").append(paging);
        } else {
            let row = document.createElement("div");
            let col = document.createElement("div");
            row.className = "row";
            col.className = "col";
            col.innerText = "No Users Found";
            row.appendChild(col);
            $("#results").append(row);
        }
    }
});
