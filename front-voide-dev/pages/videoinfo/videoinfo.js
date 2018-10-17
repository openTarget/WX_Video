const app = getApp()

Page({
  data: {
    cover: 'cover'
  },
  showSearch () {
    wx.navigateTo({
      url: '../seek/seek',
    })
  }
})