//JS manage toppings
$(document).ready(function () {
    // Fetch + display toppings
    $.ajax({
        url: "https://54.183.203.254/pizza/api/topping",
        method: "GET"
    }).done(function (result) {
        const listTopping = result.data;
        let html = `<h2 class="menu-section-title">Toppings</h2>`;
        for (let i = 0; i < listTopping.length; i++) {
            html += `<div class="menu-item" data-id="${listTopping[i].id}">
                        <div class="menu-item-name">${listTopping[i].name}</div>
                        <div class="upde_button">
                            <button type="button" id = "update_topping" class="check_button" data-id="${listTopping[i].id}" data-name="${listTopping[i].name}">Update</button>
                            <button type="button" id = "delete_topping" class="check_button" data-id="${listTopping[i].id}">Delete</button>
                        </div>
                     </div>`;
        }
        $('#topping-list').append(html);
    }).fail(function (jqXHR, textStatus, errorThrown) {
        console.error("Failed to fetch toppings:", textStatus, errorThrown);
    });

    // Handle Update button click
    $(document).on("click", "#update_topping", function () {
        const toppingId = $(this).data("id");
        const toppingName = $(this).data("name");

        // Show current topping name
        $("#updateToppingModal input[name='toppingId']").val(toppingId);
        $("#updateToppingModal input[name='toppingName']").val(toppingName);
        $("#updateToppingModal").show(); 
    });

    // Handle Update Submit
    $("#updateToppingForm").submit(function (event) {
        event.preventDefault(); 

        const toppingId = $("input[name='toppingId']").val();
        const newToppingName = $("input[name='toppingName']").val().trim();

        if (newToppingName === "") {
            Swal.fire({
                title: "Error",
                text: "Please enter a topping.",
                icon: "warning",
                customClass: {
                    title: "swal-title",
                    htmlContainer: "swal-text"
                }
            });
            return;
        }

        $.ajax({
            url: `https://54.183.203.254/pizza/api/topping/update`,
            method: "POST",
            data: { id: toppingId, name: newToppingName },
            success: function (response) {
                Swal.fire({
                    title: "Success",
                    text: response.message,
                    icon: "success",
                    customClass: {
                        title: "swal-title",
                        htmlContainer: "swal-text"
                    }});
                $(`.menu-item[data-id="${toppingId}"] .menu-item-name`).text(newToppingName); // Update UI
                $("#updateToppingModal").hide(); 
            },
            error: function (xhr) {
                let response = xhr.responseJSON;
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
        });
    });

    // Cancel is clicked
    $("#cancelUpdate").click(function () {
        $("#updateToppingModal").hide(); 
    });

    // Handle Delete button click 
    $(document).on("click", "#delete_topping", function () {
        const toppingId = $(this).data("id");

        Swal.fire({
            title: "Are you sure?",
            text: "This topping will be deleted permanently!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Yes, delete it!",
            customClass: {
                 title: "swal-title",
                htmlContainer: "swal-text",
                confirmButton: 'custom-button',
                cancelButton: 'custom-button',
                okButton: 'custom-button'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    url: `https://54.183.203.254/pizza/api/topping/delete/${toppingId}`,
                    method: "POST",
                    success: function (response) {
                        Swal.fire("Deleted!", response.message, "success");
                        $(`.menu-item[data-id="${toppingId}"]`).remove(); // Remove deleted topping from UI
                    }
                });
            }
        });
    });
});


 
 // Handle adding toppings
 $(document).ready(function () {
    $("#toppingForm").submit(function (event) {
        event.preventDefault(); 
        const toppingName = $("#toppingInput").val().trim();
        if (toppingName === "") {
            Swal.fire({
                title: "Error",
                text: "Please enter a topping.",
                icon: "warning",
                customClass: {
                    title: "swal-title",
                    htmlContainer: "swal-text"
                }
            });
            return;
        }

        $.ajax({
            url: "https://54.183.203.254/pizza/api/topping/adding",
            method: "POST",
            data: { name: toppingName },
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
            },error: function (xhr) {
                let response = xhr.responseJSON;
                Swal.fire({
                    title: "Error",
                    text: response && response.message ? response.message : "An error occurred while adding the topping.",
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

