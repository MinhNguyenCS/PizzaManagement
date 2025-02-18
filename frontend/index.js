//display multiselect dropdown list for toppings
document.addEventListener("DOMContentLoaded", function () {
    const dropdown = document.querySelector(".multiselect-dropdown");
    const toppingShow = dropdown.querySelector(".topping-show");
    const selectedContainer = dropdown.querySelector(".selectedList-container");

    dropdown.addEventListener("click", function (event) {
        dropdown.classList.toggle("show");
    });
    // topping selection
    toppingShow.addEventListener("click", function (event) {
        if (event.target.tagName === "LI") {
            const value = event.target.dataset.value;
            if (!isAlreadySelected(value)) {
                addSelectedItem(value);
            }
        }
    });
     //show selected toppings in the show box
    function addSelectedItem(value) {
        const item = document.createElement("div");
        item.classList.add("selectedItem");
        item.innerHTML = `${value} <i class='fa-solid fa-times'></i>`;
        item.querySelector(".fa-times").addEventListener("click", function () {
            item.remove();
        });
        selectedContainer.appendChild(item);
    }
    // check the selected topping
    function isAlreadySelected(value) {
        return [...selectedContainer.children].some(item => item.textContent.includes(value));
    }

    document.addEventListener("click", function (event) {
        if (!dropdown.contains(event.target)) {
            dropdown.classList.remove("show");
        }
    });
});

// mangage the dropdown for toppings show in the pop-up window shen updating pizza
document.addEventListener("DOMContentLoaded", function () {
    const toppingDropdown = document.querySelector(".topping-dropdown");
    const dropdownButton = toppingDropdown.querySelector(".dropdown-button");
    const toppingList = toppingDropdown.querySelector(".topping-dropdown-list");
    const selectedContainer = toppingDropdown.querySelector(".selected-toppings-container");

    // test sample
    const toppings = [
        { id: 1, name: "Pepperoni" },
        { id: 2, name: "Mushrooms" },
        { id: 3, name: "Onions" },
    ];

    // populate dropdown list
    toppings.forEach(topping => {
        const li = document.createElement("li");
        li.dataset.id = topping.id;
        li.dataset.value = topping.name;
        li.textContent = topping.name;
        toppingList.appendChild(li);
    });

    // toggle visibility
    dropdownButton.addEventListener("click", function (event) {
        toppingDropdown.classList.toggle("show");
        event.stopPropagation();
    });

    // topping selection
    toppingList.addEventListener("click", function (event) {
        if (event.target.tagName === "LI") {
            const value = event.target.dataset.value;
            const id = event.target.dataset.id;
            if (!isAlreadySelected(value)) {
                addSelectedItem(value, id);
            }
        }
    });

    // ddd selected item
    function addSelectedItem(value, id) {
        if (!value || !id) return; 
        const item = document.createElement("div");
        item.classList.add("selected-topping");
        item.dataset.id = id;
        item.innerHTML = `${value} <i class='fa-solid fa-times'></i>`;

        item.querySelector(".fa-times").addEventListener("click", function () {
            item.remove();
        });

        selectedContainer.appendChild(item);
    }

    // check if already selected
    function isAlreadySelected(value) {
        return [...selectedContainer.children].some(item => item.textContent.includes(value));
    }

    // close dropdown if clicked outside
    document.addEventListener("click", function (event) {
        if (!toppingDropdown.contains(event.target)) {
            toppingDropdown.classList.remove("show");
        }
    });
});

//display userId 
document.addEventListener('DOMContentLoaded', function() {
    let userId = localStorage.getItem('userId');
    if (!userId) {
        userId = "Guest";
    }
    document.getElementById('welcome_user').textContent = userId;
});
