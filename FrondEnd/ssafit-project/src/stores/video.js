// stores/video.js
import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useVideoStore = defineStore("video", () => {
  const videoList = ref([]);
  const selectedVideos = ref([]);
  const selectVideo = ref(null);
  const isLoading = ref(false);
  const error = ref(null);

  // 유튜브 비디오 검색 요청 날리기
  const youtubeSearch = (keyword) => {
    isLoading.value = true;
    error.value = null;

    const url = "https://www.googleapis.com/youtube/v3/search";
    const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

    return axios({
      url,
      method: "GET",
      params: {
        key: API_KEY,
        part: "snippet",
        maxResults: 5,
        order: "relevance",
        q: keyword,
        type: "video",
        videoDuration: "medium",
        // regionCode: "KR", // 한국 지역 설정
      },
    })
      .then(async (res) => {
        // console.log("res.data", res.data);

        // 1. 비디오 ID 목록 추출
        const videoIds = res.data.items
          .map((item) => item.id.videoId)
          .join(",");

        // 2. 비디오 상세 정보 요청
        return axios({
          url: "https://www.googleapis.com/youtube/v3/videos",
          method: "GET",
          params: {
            key: API_KEY,
            part: "snippet,statistics,contentDetails,status",
            id: videoIds,
          },
        });
      })
      .then((detailRes) => {
        // console.log("Video Details Response:", detailRes.data);

        // 3. embeddable 비디오 필터링
        const embeddableVideos = detailRes.data.items.filter(
          (video) => video.status.embeddable
        );

        // 4. 비디오 리스트 업데이트
        videoList.value = embeddableVideos;
      })
      .catch((err) => {
        console.error("Error searching videos:", err);
        error.value = "비디오 검색 중 오류가 발생했습니다.";
        throw error.value;
      })
      .finally(() => {
        isLoading.value = false;
      });
  };

  const clickVideo = (video) => {
    selectVideo.value = video;
  };

  // 비디오 선택하기
  const toggleVideoSelection = (video) => {
    const index = selectedVideos.value.findIndex((v) => v.id === video.id);
    if (index === -1) {
      selectedVideos.value.push(video);
    } else {
      selectedVideos.value.splice(index, 1);
    }
  };

  // 비디오 ID 목록으로 비디오 정보 가져오기
  const getVideosByIds = async (videoIds) => {
    try {
      isLoading.value = true;
      error.value = null;

      const url = "https://www.googleapis.com/youtube/v3/videos";
      const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

      const response = await axios.get(url, {
        params: {
          key: API_KEY,
          part: "snippet",
          id: videoIds.join(","),
        },
      });

      // YouTube API 응답을 프로그램에서 사용하는 형식으로 변환
      return response.data.items.map((item) => ({
        id: { videoId: item.id },
        snippet: item.snippet,
      }));
    } catch (err) {
      console.error("Error fetching videos:", err);
      error.value = "비디오 정보를 가져오는 중 오류가 발생했습니다.";
      throw error.value;
    } finally {
      isLoading.value = false;
    }
  };

  // 프로그램에 등록된 비디오 모두 조회하기
  const getVideosByProgramId = async (programId) => {
    try {
      isLoading.value = true;
      error.value = null;

      // 프로그램 정보에서 비디오 ID 목록을 가져옵니다
      const response = await axios.get(`/api/programs/${programId}`);
      const videoIds = response.data.videoIds || [];

      // 비디오 ID가 있는 경우에만 YouTube API 호출
      if (videoIds.length > 0) {
        const videos = await getVideosByIds(videoIds);
        videoList.value = videos;
        return videos;
      } else {
        videoList.value = [];
        return [];
      }
    } catch (err) {
      console.error("Error fetching program videos:", err);
      error.value = "프로그램의 비디오 목록을 가져오는 중 오류가 발생했습니다.";
      throw error.value;
    } finally {
      isLoading.value = false;
    }
  };
  // 선택된 비디오 설정 (프로그램 수정 시 사용)
  const setSelectedVideos = (videos) => {
    selectedVideos.value = [...videos];
  };

  // videoList 초기화 메서드 추가
  const clearVideoList = () => {
    videoList.value = [];
  };

  const clearSelectedVideos = () => {
    selectedVideos.value = [];
    selectVideo.value = null;
  };

  // 모든 상태 초기화
  const clearAll = () => {
    clearVideoList();
    clearSelectedVideos();
  };

  return {
    // state
    videoList,
    selectedVideos,
    selectVideo,
    isLoading,
    error,

    // actions
    youtubeSearch,
    clickVideo,
    toggleVideoSelection,
    clearSelectedVideos,
    getVideosByIds,
    setSelectedVideos,
    clearAll,
    clearVideoList,
    getVideosByProgramId,
  };
});
