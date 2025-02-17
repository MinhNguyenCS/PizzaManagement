
//JS manage pizzas
$(document).ready(function () {
    // Fetch + display toppings
    $.ajax({
        url: "https://54.183.203.254/pizza/api/topping",
        method: "GET"
    }).done(function (result) {
        const listTopping = result.data;
        let html = "";
        for (let i = 0; i < listTopping.length; i++) {
            html += `<li data-id="${listTopping[i].id}" data-value="${listTopping[i].name}">${listTopping[i].name}</li>`;
        }
        $('#topping-available').html(html);

        // Topping selection
        $(document).on("click", "#topping-available li", function () {
            $(this).toggleClass("selected");
        });
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Failed to fetch toppings:", textStatus, errorThrown);
    });

    // Fetch + display pizzas
    $.ajax({
        url: "https://54.183.203.254/pizza/api/chef",
        method: "GET"
    }).done(function (result) {
        const listPizza = result.data;
        let html = `<h2 class="menu-section-title">Pizza</h2>`;
        for (let i = 0; i < listPizza.length; i++) {
            let toppingNames = listPizza[i].toppings.map(topping => topping.name).join(', ');
            html += `<div class="pizza-item" data-id="${listPizza[i].id}">
              <div class="menu-item-name">${listPizza[i].name}</div>
              <div class="upde_pizza">
                <button type="submit" id="update_pizza" class="check_button" data-id="${listPizza[i].id}" data-name="${listPizza[i].name}">Update</button>
                <button type="submit" id="delete_pizza" class="check_button" data-id="${listPizza[i].id}">Delete</button>
              </div>
              <div class="menu-item-description">${toppingNames}</div>
            </div>`;
        }
        $('#pizza-list').append(html);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Failed to fetch pizzas:", textStatus, errorThrown);
    });

    // Update button click
    $(document).on("click", "#update_pizza", function () {
        const pizzaId = $(this).data("id");
        const pizzaName = $(this).data("name");

        // Show current pizz name
        $("#updatePizzaModal input[name='pizzaId']").val(pizzaId);
        $("#updatePizzaModal input[name='pizzaName']").val(pizzaName);

        $.ajax({
            url: `https://54.183.203.254/pizza/api/chef/${pizzaId}`,
            method: "GET"
        }).done(function (response) {
            if (response.success) {
                const pizza = response.data;
                
                // Toppings dropdown
                $.ajax({
                    url: `https://54.183.203.254/pizza/api/topping`,
                    method: "GET"
                }).done(function (result) {
                    const listTopping = result.data;
                    let html = "";
                    listTopping.forEach(topping => {
                        const isSelected = pizza.toppings.some(pt => pt.id === topping.id);
                        html += `<li data-id="${topping.id}" class="${isSelected ? 'selected' : ''}">${topping.name}</li>`;
                    });
                    $('.topping-dropdown-list').html(html);
                });

                // Selected toppings
                $(".selected-toppings-container").empty();
                pizza.toppings.forEach(topping => {
                    $(".selected-toppings-container").append(
                        `<div class="selected-topping" data-id="${topping.id}">
                            ${topping.name} <i class='fa-solid fa-times remove-topping'></i>
                        </div>`
                    );
                });

                $("#updatePizzaModal").show();
            }
        });
    });

    // Topping selection inside pop-up
    $(document).on("click", ".topping-dropdown-list li", function () {
        $(this).toggleClass("selected");
        let toppingId = $(this).attr("data-id");
        let toppingName = $(this).text().trim();
    
        if (!toppingId || !toppingName) return; 
    
        if ($(this).hasClass("selected")) {
            $(".selected-toppings-container").append(
                `<div class="selected-topping" data-id="${toppingId}">
                    ${toppingName} <i class='fa-solid fa-times remove-topping'></i>
                </div>`
            );
        } else {
            $(`.selected-toppings-container .selected-topping[data-id="${toppingId}"]`).remove();
        }
    });

    // Update Submit
    $("#updatePizzaForm").submit(function (event) {
        event.preventDefault();

        const pizzaId = $("input[name='pizzaId']").val();
        const newPizzaName = $("input[name='pizzaName']").val().trim();
        if (newPizzaName === "") {
            Swal.fire({ title: "Error", text: "Please enter a pizza name.", icon: "warning" });
            return;
        }

        let toppingIds = [];
        $(".selected-topping").each(function () {
            let toppingId = $(this).attr("data-id");
            if (toppingId) {
                toppingIds.push(toppingId);
            }
        });

        const requestData = JSON.stringify({ name: newPizzaName, toppingIds });
        // Get pizza by id
        $.ajax({
            url: `https://54.183.203.254/pizza/api/chef/update/${pizzaId}`,
            method: "POST",
            contentType: "application/json",
            data: requestData
        }).done(function (response) {
            Swal.fire({ title: "Success", text: response.message, icon: "success" })
                .then(() => {
                    location.reload();
                    $("#updatePizzaModal").hide();
                });
        }).fail(function (xhr) {
            let response = xhr.responseJSON;
            Swal.fire({ title: "Oops!", text: response.message, icon: "error" });
        });
    });

    // Remove topping from selected
    $(document).on("click", ".remove-topping", function () {
        let toppingId = $(this).parent().data("id");
        $(`.topping-dropdown-list li[data-id="${toppingId}"]`).removeClass("selected");
        $(this).parent().remove();
    });

    $("#cancelPizzaModal").click(function () {
        $("#updatePizzaModal").hide();
    });


    // Delete pizza
    $(document).on("click", "#delete_pizza", function () {
        const pizzaId = $(this).data("id");

        Swal.fire({
            title: "Are you sure?",
            text: "This pizza will be deleted permanently!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Yes, delete it!",
            customClass: {
                title: "swal-title",
                htmlContainer: "swal-text",
                confirmButton: 'custom-button',
                cancelButton: 'custom-button'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: `https://54.183.203.254/pizza/api/chef/delete/${pizzaId}`,
                    method: "POST",
                    success: function (response) {
                        Swal.fire("Deleted!", response.message, "success");
                        $(`.pizza-item[data-id="${pizzaId}"]`).remove(); // Reload
                    },
                    error: function (xhr) {
                        let response = xhr.responseJSON;
                        Swal.fire("Error", response && response.message ? response.message : "Failed to delete pizza.", "error");
                    }
                });
            }
        });
    });

    // Adding new pizza
    $("#pizzaForm").submit(function (event) {
        event.preventDefault();
        
        const pizzaName = $("#pizzaInput").val().trim();
        if (pizzaName === "") {
            Swal.fire({
                title: "Error",
                text: "Please enter pizza name.",
                icon: "warning",
                customClass: {
                    title: "swal-title",
                    htmlContainer: "swal-text"
                }
            });
            return;
        }

        // Get selected topping IDs
        let toppingIds = [];
        $("#topping-available li.selected").each(function () {
            toppingIds.push($(this).attr("data-id"));
        });

        const requestData = JSON.stringify({
            name: pizzaName,
            toppingIds: toppingIds
        });

        $.ajax({
            url: "https://54.183.203.254/pizza/api/chef/adding",
            method: "POST",
            contentType: "application/json",
            data: requestData,
            success: function (response) {
                if (response.success) {
                    Swal.fire({
                        title: "Success",
                        text: response.message,
                        icon: "success",
                        customClass: {
                            title: "swal-title",
                            htmlContainer: "swal-text"
                        }
                    }).then(() => {
                        location.reload();
                    });
                } else {
                    Swal.fire({
                        title: "Oops!",
                        text: response.message,
                        icon: "error",
                        customClass: {
                            title: "swal-title",
                            htmlContainer: "swal-text"
                        }
                    });
                }
            },
            error: function (xhr) {
                let response = xhr.responseJSON;
                Swal.fire({
                    title: "Error",
                    text: response && response.message ? response.message : "An error occurred while adding the pizza.",
                    icon: "error",
                    customClass: {
                        title: "swal-title",
                        htmlContainer: "swal-text"
                    }
                });
            }
        });
    });
});
