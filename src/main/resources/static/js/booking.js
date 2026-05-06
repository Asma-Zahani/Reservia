let bookingList = [];
/* =========================
   ADD TO BOOKING
========================= */
window.addToBooking = function(id, type, price, image) {

    let existing = bookingList.find(r => r.id === id);

    if (existing) {
        existing.qty++;
    } else {
        bookingList.push({
            id,
            type,
            price,
            image,
            qty: 1
        });
    }

    renderSelection();
    updateTotal();
    updateExtrasState();
};

/* =========================
   RENDER SELECTION
========================= */
function renderSelection() {
    let container = document.getElementById("selectionContainer");
    let title = document.getElementById("selectionTitle");

    container.innerHTML = "";

    if (bookingList.length === 0) {
        title.style.display = "none";
        return;
    }

    title.style.display = "block";

    bookingList.forEach(room => {
        container.innerHTML += `
        <div class="latest__post mb-20 text-start">
            <div class="single__post">
                <div class="single__post__thumb">
                    <img src="${room.image}" height="115" width="120">
                </div>

                <div class="single__post__meta">
                    <a class="font-sm text-truncate d-block" style="max-width:210px;">
                        ${room.type}
                    </a>

                    <span>${room.price} TND / Night</span>

                    <div class="quantity-box">
                         <input type="hidden" name="roomIds" value="${room.id}">
                         <input type="hidden" name="room_${room.id}_qty" value="${room.qty}">
                        <button type="button" onclick="changeQty(${room.id}, -1)">−</button>
                        <input type="number" value="${room.qty}" readonly>
                        <button type="button" onclick="changeQty(${room.id}, 1)">+</button>
                    </div>
                </div>
            </div>
        </div>
        `;
    });
}

/* =========================
   CHANGE QTY
========================= */
window.changeQty = function(id, delta) {

    let room = bookingList.find(r => r.id === id);
    if (!room) return;

    room.qty += delta;

    if (room.qty <= 0) {
        bookingList = bookingList.filter(r => r.id !== id);
    }

    renderSelection();
    updateTotal();
};

/* =========================
   GET NIGHTS
========================= */
function parseDate(str) {
    let parts = str.split("-");
    return new Date(parts[2], parts[1] - 1, parts[0]);
}

function getNights() {
    let checkIn = document.getElementById("check__in_2")?.value;
    let checkOut = document.getElementById("check__out_2")?.value;

    if (!checkIn || !checkOut) return 1;

    let inDate = parseDate(checkIn);
    let outDate = parseDate(checkOut);

    let diff = (outDate - inDate) / (1000 * 60 * 60 * 24);

    return diff > 0 ? diff : 1;
}

/* =========================
   UPDATE TOTAL
========================= */
window.updateTotal = function() {

    let nights = getNights();
    let total = 0;

    bookingList.forEach(room => {
        total += room.price * room.qty * nights;
    });

    document.querySelectorAll('input[type="checkbox"]:checked').forEach(el => {

        let price = parseInt(el.dataset.price || 0);
        let perNight = el.dataset.perNight === "true";

        if (perNight) {
            total += price * nights;
        } else {
            total += price;
        }

    });

    document.getElementById("totalPrice").innerText = total;

    let totalInput = document.getElementById("totalPriceInput");
    if (totalInput) {
        totalInput.value = total;
    }
};

/* =========================
   QUANTITY SIMPLE INPUT
========================= */
window.increaseQty = function() {
    let input = document.getElementById("qty");
    input.value = parseInt(input.value) + 1;
    updateTotal();
};

window.decreaseQty = function() {
    let input = document.getElementById("qty");
    if (input.value > 1) {
        input.value = parseInt(input.value) - 1;
        updateTotal();
    }
};

/* =========================
   EXTRAS
========================= */
function updateExtrasState() {

    let hasRoom = bookingList.length > 0;

    document.querySelectorAll('.query__input.checkbox input').forEach(el => {
        el.disabled = !hasRoom;
    });
}
/* =========================
   INIT
========================= */
window.addEventListener("load", updateTotal);