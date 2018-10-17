const app = getApp()

Page({
  data: {
    totalPage: 1, // 总页数
    page: 1, // 当前页数
    videoList: [],
    screenWidth: 350, // 手机的宽度
    serverUrl:'' // 图片展示地址
  },

  onLoad: function (params) {
    var me = this;
    // 微信同步手机信息，获取手机的宽度
    var screenWidth = wx.getSystemInfoSync().screenWidth;
    console.log(screenWidth)
    me.setData({
      screenWidth: screenWidth,
    });

    // 获取当前的分页数
    var page = me.data.page;
    me.getAllVideoList(page)
  },

  getAllVideoList (page) {
    var me = this;
    var serverUrl = app.serverUrl;
    wx.showLoading({
      title: '正在玩命加载中',
    })

    wx.request({
      url: serverUrl + '/video/showAll?page=' + page,
      method: 'post',
      success(res) {
        wx.hideLoading();
        wx.hideNavigationBarLoading()
        console.log(res.data)

        // 判断当前页是不是为第一页，如果是第一页，那么设置videoList为空
        if (page === 1) {
          me.setData({
            videoList: []
          })
        }

        //将取出数据库中获取的数据
        var videoList = res.data.data.rows
        var newVideoList = me.data.videoList

        me.setData({
          videoList: newVideoList.concat(videoList),
          page: page,
          totalPage: res.data.data.total,
          serverUrl: serverUrl
        })
      }
    })
  },
  onReachBottom () {
    var me = this
    console.log('上拉')
    var currentPage = me.data.page;
    var totalPage = me.data.totalPage;

    // 判断当前页数和总页数是否相等，相等得话无需查询
    if(currentPage === totalPage) {
      wx.showToast({
        title: '已经没有视频了··',
        icon: 'none'
      })
      return;
    }
    var page = currentPage + 1
    me.getAllVideoList(page)
  },
  onPullDownRefresh () {
    console.log(123)
    wx.showNavigationBarLoading()
    this.getAllVideoList(1)
  }
})
