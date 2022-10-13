const NATIVE_LOG_MESSAGE_ID = "native_logs__mesage";
const LOCAL_NOTIFICATION_DISPLAY_TIME = "local_notifications__display-time";
const LOCAL_NOTIFICATION_ADD_ID = "local_notifications__add-id";
const LOCAL_NOTIFICATION_TITLE = "local_notifications__title";
const LOCAL_NOTIFICATION_MESSAGE = "local_notifications__message";
const LOCAL_NOTIFICATION_DELETE_ID = "local_notifications__delete-id";
const ERROR_MESSAGE_CONTAINER_ID = "error";
const ERROR_MESSAGE_TEXT_ID = "error__message";
const ERROR_TYPE_MESSAGE = "error";
const LOG_TYPE_MESSAGE = "log";

var notifications = [];
var lifecycleEvents = [];
var appInterface = !!window.Android ? window.Android : mockApp;

function onLoadApp() {
  document.getElementById(LOCAL_NOTIFICATION_DISPLAY_TIME).value =
    formatDateToIso(Date.now());

  document.getElementById(LOCAL_NOTIFICATION_DISPLAY_TIME).min = new Date()
    .toISOString()
    .substring(0, 16);

  const param = new URLSearchParams(window.location.search);

  if (!param.get("app")) return showErrorMessage("App identifier expected");

  const appContainer = document.getElementById(param.get("app"));

  if (!appContainer)
    return showErrorMessage("The specified app does not exist.");

  appContainer.style.display = "flex";

  switch (param.get("app")) {
    case "local_notifications":
      getNotifications();
      break;
    case "lifecycle_events":
      getLifecycleEvents();
      break;
  }
}

function showErrorMessage(message) {
  document.getElementById(ERROR_MESSAGE_CONTAINER_ID).style.display = "flex";
  document.getElementById(ERROR_MESSAGE_TEXT_ID).innerText = message;
}

function showMessageFromWebView() {
  const message = document.getElementById(NATIVE_LOG_MESSAGE_ID).value;

  if (!message.length)
    return showToast("Message should not be empty", ERROR_TYPE_MESSAGE);

  appInterface.showMessageFromWebView(message);

  clearInputs(`#${LOCAL_NOTIFICATION_DISPLAY_TIME}`);
}

function getNotifications() {
  const tableBody = document.querySelector("#local_notifications__table");
  const setNotifications = appInterface.getNotifications();

  if (!setNotifications || !setNotifications.length) {
    return showToast("No notifications to show", LOG_TYPE_MESSAGE);
  }

  notifications = JSON.parse(setNotifications);

  if (!notifications.length)
    return (tableBody.innerHTML = "<h1>No data to show</h1>");

  const tableData = notifications
    .map(
      (value) => `<tr>
                      <td>${formatDateToReadable(value.displayTime)}</td>
                      <td>${value.id}</td>
                      <td>${value.title}</td>
                      <td>${value.message}</td>
                  </tr>`
    )
    .join("");

  tableBody.innerHTML = tableData;

  showToast("Notifications table updated!", LOG_TYPE_MESSAGE);
}

function addNotification() {
  const displayTime = formatDateToIso(
    document.getElementById(LOCAL_NOTIFICATION_DISPLAY_TIME).value
  );
  const id = document.getElementById(LOCAL_NOTIFICATION_ADD_ID).value;
  const title = document.getElementById(LOCAL_NOTIFICATION_TITLE).value;
  const message = document.getElementById(LOCAL_NOTIFICATION_MESSAGE).value;

  if (!displayTime || !id || !title || !message)
    return showToast("All values must be filled", ERROR_TYPE_MESSAGE);

  appInterface.addNotification(displayTime, Number(id), title, message);

  if (document.getElementById("local_notifications__refresh_checkbox").checked)
    getNotifications();

  clearInputs(
    `#${LOCAL_NOTIFICATION_DISPLAY_TIME}, #${LOCAL_NOTIFICATION_TITLE}, #${LOCAL_NOTIFICATION_ADD_ID}, #${LOCAL_NOTIFICATION_MESSAGE}`
  );
}

function deleteNotification() {
  const notificationId = document.getElementById(
    LOCAL_NOTIFICATION_DELETE_ID
  ).value;

  if (!notificationId)
    return showToast("No ID was provided", ERROR_TYPE_MESSAGE);

  const notificationToDelete = notifications.find(
    (notification) => notification.id == notificationId
  );

  if (!notificationToDelete)
    return showToast(
      `Notification with ID ${notificationId} not found`,
      ERROR_TYPE_MESSAGE
    );

  appInterface.deleteNotification(JSON.stringify(notificationToDelete));

  if (document.getElementById("local_notifications__refresh_checkbox").checked)
    getNotifications();

  clearInputs(`#${LOCAL_NOTIFICATION_DELETE_ID}`);
}

function getLifecycleEvents() {
  const tableBody = document.querySelector("#lifecycle_events__table");
  const setLifecycleEvents = appInterface.getLifecycleEvents();

  if (!setLifecycleEvents || !setLifecycleEvents.length) {
    return showToast("No lifecycle events to show", LOG_TYPE_MESSAGE);
  }

  lifecycleEvents = JSON.parse(setLifecycleEvents);

  if (!lifecycleEvents.length)
    return (tableBody.innerHTML = "<h1>No data to show</h1>");

  const tableData = lifecycleEvents
    .map(
      (value) => `<tr>
                      <td>${value.id}</td>
                      <td>${value.eventName}</td>
                      <td>${formatDateToReadable(value.createdAt)}</td>
                  </tr>`
    )
    .join("");

  tableBody.innerHTML = tableData;

  showToast("Lifecycle events table updated!", LOG_TYPE_MESSAGE);
}

function closeWebView() {
  appInterface.closeWebView();
}

function showToast(message, type) {
  appInterface.showToast(message, type);
}

function formatDateToIso(date) {
  if (!date) return;

  var now = new Date(date);
  now.setMinutes(now.getMinutes() - now.getTimezoneOffset());

  return now.toISOString().slice(0, 16);
}

function formatDateToReadable(date) {
  return new Date(date).toLocaleDateString("en-us", {
    weekday: "long",
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

function clearInputs(list) {
  const inputs = document.querySelectorAll(list);

  if (!inputs) return;

  inputs.forEach((input) => {
    switch (input.type) {
      case "datetime-local":
        input.value = formatDateToIso(new Date());
        break;
      default:
        input.value = "";
        break;
    }
  });
}

function handleRefreshButtonState() {
  var checkBox = document.getElementById(
    "local_notifications__refresh_checkbox"
  );
  var refreshButton = document.getElementById(
    "local_notifications__refresh_button"
  );

  if (checkBox.checked == true) {
    refreshButton.disabled = true;
  } else {
    refreshButton.disabled = false;
  }
}
