function mockNotificationsData() {
  return JSON.stringify([
    {
      displayTime: "2022-11-20T10:40",
      id: 2,
      message: "You got a new mail!",
      title: "New Notification",
    },
    {
      displayTime: "2022-11-20T10:40",
      id: 3,
      message: "You got a new mail!",
      title: "New Notification",
    },
    {
      displayTime: "2022-11-21T10:40",
      id: 1,
      message: "You got a new mail!",
      title: "New Notification",
    },
  ]);
}

function mockLifecycleEventsData() {
  return JSON.stringify([
    {
      id: 1,
      eventName: "onActivityResumed",
      createdAt: 1665670728693,
    },
    {
      id: 2,
      eventName: "onActivityResumed",
      createdAt: 1665670728693,
    },
  ]);
}

const mockApp = {
  showToast: function (message, type) {
    switch (type) {
      case "log":
        console.log(message);
        break;
      case "error":
        console.error(message);
        break;
      default:
        break;
    }
  },
  showMessageFromWebView: function (message) {
    if (!message.length)
      return mockApp.showToast(
        "Message should not be empty",
        ERROR_TYPE_MESSAGE
      );

    mockApp.showToast(message, LOG_TYPE_MESSAGE);
  },
  addNotification: function (displayTime, id, title, message) {
    mockApp.showToast(
      `Added notification with: 
        displayTime: ${displayTime}, 
        id: ${id},
        title: ${title},
        message: ${message}
      `,
      LOG_TYPE_MESSAGE
    );
  },
  deleteNotification: function (notification) {
    const { displayTime, id, title, message } = JSON.parse(notification);

    mockApp.showToast(
      `Deleted notification with: 
        displayTime: ${displayTime}, 
        id: ${id},
        title: ${title},
        message: ${message}
      `,
      LOG_TYPE_MESSAGE
    );
  },
  getNotifications: function () {
    return mockNotificationsData();
  },
  getLifecycleEvents: function () {
    return mockLifecycleEventsData();
  },
  closeWebView: function () {
    mockApp.showToast("Webview should close", LOG_TYPE_MESSAGE)
  }
};
