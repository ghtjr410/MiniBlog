import axios, { AxiosResponse } from "axios";
import { CheckCertificationRequestDto, EmailCertificationRequestDto, IdCheckRequestDto, SignInRequestDto, SignUpRequestDto, NicknameRequestDto } from "utils/DtoUtil/request/auth";
import { CheckCertificationResponseDto, EmailCertificationResponseDto, IdCheckResponseDto, SignInResponseDto, SignUpResponseDto } from "utils/DtoUtil/response/auth";
import { ResponseDto } from "utils/DtoUtil/response";
import NicknameResponseDto from "utils/DtoUtil/response/auth/nickname.response.dto";
// import { getCookie, setCookie } from "utils/CookieUtil/cookieUtis";
import { getDeviceInfo } from "utils/DeviceInfoUtil/deviceInfoUtil";
import { getCookie, setCookie } from "utils/CookieUtil/cookieUtis";
import { SIGN_IN_URL, SIGN_UP_URL, ID_CHECK_URL, EMAIL_CERTIFICATION_URL, CHECK_CERTIFICATION_URL, NICKNAME_CHECK_URL, NICKNAME_CREATE_URL, NICKNAME_FIND_URL, LOGOUT_URL, REFRESH_TOKEN_URL, AAA_URL, GET_ALL_POSTS, CHECK_POST_OWNER, LIKE_CLICK_URL, POST_IS_LIKED_URL, DELETE_MY_POST_URL, POST_ADD_COMMENT, GET_PUBLIC_POST, GET_PRIVATE_POST, POST_DELETE_COMMENT, POST_EDIT_COMMENT, POST_INSERT_POST, POST_DELETE_ACCOUNT, PUT_UPDATE_POST, INCREMENT_VIEW } from "utils/APIUrlUtil/apiUrlUtil";

const responseHandler = <T> (response: AxiosResponse<any,any>) => {
    const responseBody: T = response.data;
    return responseBody;
}

const errorHandler = (error: any) => {
    console.error('API Request Error:', error);
    if (error.response && error.response.data) {
        const responseBody: ResponseDto = error.response.data;
        return responseBody;
    } else {
        return null;
    }
}

export const signInRequest = async (requestBody: SignInRequestDto) => {
    const result = await axios.post(SIGN_IN_URL(), requestBody)
        .then(responseHandler<SignInResponseDto>)
        .catch(errorHandler);
    return result;
}
/////////////////////////////////////////////////////////////////////////
export const signUpRequest = async (requestBody: SignUpRequestDto) => {
    const result = await axios.post(SIGN_UP_URL(), requestBody)
        .then(responseHandler<SignUpResponseDto>)
        .catch(errorHandler);
    return result;
};

export const idCheckRequest = async (requestBody: IdCheckRequestDto) => {
    const result = await axios.post(ID_CHECK_URL(), requestBody)
        .then(responseHandler<IdCheckResponseDto>)
        .catch(errorHandler);
    return result;
};

export const emailCertificationRequest = async (requestBody: EmailCertificationRequestDto) => {
    const result = await axios.post(EMAIL_CERTIFICATION_URL(), requestBody)
        .then(responseHandler<EmailCertificationResponseDto>)
        .catch(errorHandler);
    return result;
};

export const checkCertificationRequest = async (requestBody: CheckCertificationRequestDto) => {
    const result = await axios.post(CHECK_CERTIFICATION_URL(), requestBody)
        .then(responseHandler<CheckCertificationResponseDto>)
        .catch(errorHandler);
    return result;

};

export const checkNicknameRequest = async (requestBody: NicknameRequestDto) => {
    const result = await axios.post(NICKNAME_CHECK_URL(), requestBody, {
        headers: {
            Authorization: `Bearer ${getCookie('accessToken')}`
        }
    })
    .then(responseHandler<NicknameResponseDto>)
    .catch(errorHandler);
    return result;
};

export const createNicknameRequest = async (requestBody: NicknameRequestDto) => {
    const result = await axios.post(NICKNAME_CREATE_URL(), requestBody, {
        headers: {
            Authorization: `Bearer ${getCookie('accessToken')}`
        }
    })
    .then(responseHandler<NicknameResponseDto>)
    .catch(errorHandler);
    return result;
};

// 닉네임 있는지 없는지
export const findNicknameRequest = async () => {
  const result = await axios.post(NICKNAME_FIND_URL(), {}, {
      headers: {
          Authorization: `Bearer ${getCookie('accessToken')}`
      }
  })
  .then(response => response.data)
  .catch(error => {
      console.error('Error finding nickname', error);
      return null;
  });
  return result;
};

export const deleteAccount = async (nickname:string) => {
  const result = await axios.post(POST_DELETE_ACCOUNT(), 
  {nickname}, 
  {
      headers: {
          Authorization: `Bearer ${getCookie('accessToken')}`
      }
  })
  .then(response => response.data)
  .catch(error => {
      console.error('Error finding nickname', error);
      return null;
  });
  return result;
};
////////////////////////////////////////////////////////////////////////////////////


export const incrementView = async (postId: number) => {
  try{
    const response = await axios.post(INCREMENT_VIEW(postId))
    return response;
  } catch (error) {
    console.error('Error IncremnetView: ', error);
    return null;
  }
}


export const getAllPosts = async (page: number) => {
  try {
    const response = await axios.get(GET_ALL_POSTS(), {
      params: { page },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching posts:', error);
    return null;
  }
};


export const getNicknameAllPosts = async (page: number) => {
  try {
    const response = await axios.get(GET_ALL_POSTS(), {
      params: { page },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching posts:', error);
    return null;
  }
};


export const fetchPostByIdWithAuth = async (postId: string) => {
  const response = await axios.get(GET_PRIVATE_POST(postId), {
    headers: {
      Authorization: `Bearer ${getCookie('accessToken')}`
    }
  });
  console.log('Authenticated Post Data:', response.data); // 추가된 로그
  return response.data;
};

export const fetchPostById = async (postId: string) => {
  const response = await axios.get(GET_PUBLIC_POST(postId));
  console.log('Public Post Data:', response.data); // 추가된 로그
  return response.data;
};

export const checkPostOwner = async (postId: string) : Promise<boolean> => {
  try {
    const response = await axios.get(CHECK_POST_OWNER(postId), {
      headers: {
        Authorization: `Bearer ${getCookie('accessToken')}`
      }
    });
    const isOwner = response.data.owner;
    console.log("게시물 소유 여부:", isOwner);
    return isOwner;
  } catch (error) {
    console.error('게시물 소유 여부 확인 중 오류 발생:', error);
    return false;
  }
};

export const clickPostLike = async (postId : string) =>{
  try {
    const response = await axios.post(LIKE_CLICK_URL(),
    {postId} ,
    {
      headers: {
        Authorization: `Bearer ${getCookie('accessToken')}`
      }})  
    console.log("좋아요 클릭 결과 : " + response);
  } catch (error) {
    console.error("좋아요 클릭 오류", error);
  }
  
}

export const isLiked = async (postId : string): Promise<boolean> => {
  try{
    const response = await axios.post(POST_IS_LIKED_URL(),
    {postId} ,
    {
      headers: {
        Authorization: `Bearer ${getCookie('accessToken')}`
      }})
    return response.data;
  } catch (error) {
    console.error('Error checking like status', error);
    return false;
  }
}

export const deleteMyPost = async (postId: string) => {
  try {
    const response = await axios.delete(DELETE_MY_POST_URL(postId), {
      headers: {
        Authorization: `Bearer ${getCookie('accessToken')}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error deleting post', error);
    throw error;
  }
};
export const updateMyPost = async (postId: string, title: string, content: string) => {
  try {
    const response = await axios.put(PUT_UPDATE_POST(postId), {
      title,
      content
    }, {
      headers: {
        Authorization: `Bearer ${getCookie('accessToken')}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error deleting post', error);
    throw error;
  }
};

export const addComment = async (postId: string, content: string) => {
  try {
    const response = await axios.post(
      POST_ADD_COMMENT(),
      { postId, content },
      {
        headers: {
          Authorization: `Bearer ${getCookie('accessToken')}`
        }
      }
    );
    return response.data;
  } catch (error) {
    console.error('Error adding comment', error);
    throw error;
  }
};

export const deleteComment = async (commentId: number) => {
  console.log("코멘트아이디!" + commentId);
  try{
    const response = await axios.post(
      POST_DELETE_COMMENT(),
      { commentId },
      { headers: { Authorization: `Bearer ${getCookie('accessToken')}` } }
    );
    return response.data;
  } catch (error) {
    console.error('Error deleting comment', error);
    throw error;
  }
};

export const editComment = async (commentId: number, content: string) => {
  try {
    const response = await axios.post(
      POST_EDIT_COMMENT(),
      { commentId, content },
      { headers: { Authorization: `Bearer ${getCookie('accessToken')}` } }
    );
    return response.data;
  } catch (error) {
    console.error('Error editing comment', error);
    throw error;
  }
};

export const insertPost = async (title: string, content: string) => {
  try{
    const response = await axios.post(
      POST_INSERT_POST(),
      { title, content},
      { headers: { Authorization: `Bearer ${getCookie('accessToken')}`}}
    );
    return response.data;

  } catch (error) {
    console.error('Error inserting post', error);
    throw error;
  }
}


////////////////////////////////////////////////////////////////////////////////////

  export const combinedLogoutHandler = async (navigateToHome: () => void) => {
    const accessToken: string | undefined = getCookie('accessToken');
    const refreshToken: string | undefined = getCookie('refreshToken');
  
    if (accessToken || refreshToken) {
      console.log('Tokens found:', { accessToken, refreshToken });
      const result = await combinedLogout(accessToken || null, refreshToken || null, navigateToHome);
      if (!result) {
        navigateToHome();
      }
      return result;
    } else {
      console.error('토큰이 없음');
      navigateToHome();
      return false;
    }
  };

  const combinedLogout = (accessToken: string | null, refreshToken: string | null, navigateToHome: () => void) => {
    const headers: any = {};
  
    if (accessToken) {
      headers['Authorization'] = `Bearer ${accessToken}`;
    }
  
    if (refreshToken) {
      headers['Refresh-Token'] = refreshToken;
    }
  
    return axios.get(LOGOUT_URL(), {
      headers: headers,
      withCredentials: true
    })
    .then((response) => {
      if (response.status === 200) {
        console.log("로그아웃 성공");
        return true;  // 성공 시 true 반환
      } else {
        console.error("로그아웃 실패");
        navigateToHome();
        return false;
      }
    })
    .catch((error) => {
      console.error("로그아웃 실패", error);
      navigateToHome();
      return false; // 실패 시 false 반환
    });
  };

  ////////////////////////////////////////////////////////////////////////////////////
  
  const refreshTokenRequest = (refreshToken: string, deviceInfo: string) => {
    const requestBody = { deviceInfo, ipAddress: '' };
  
    return axios.post(REFRESH_TOKEN_URL(), requestBody, {
      headers: { 'Authorization': `Bearer ${refreshToken}` },
      withCredentials: true
    })
    .then((response) => {
      const { accessToken, refreshToken: newRefreshToken, accessTokenExpirationTime, refreshTokenExpirationTime } = response.data;
      
      const accessTokenExpires = new Date(accessTokenExpirationTime);
      const refreshTokenExpires = new Date(refreshTokenExpirationTime);
  
      setCookie('accessToken', accessToken, { expires: accessTokenExpires, path: '/' });
      setCookie('refreshToken', newRefreshToken, { expires: refreshTokenExpires, path: '/' });
      return accessToken;
    })
    .catch((error) => {
      console.error("토큰 갱신 실패", error);
      return null;
    });
  };
  
  const handleTokenRefresh = (refreshToken: string, deviceInfo: string, navigateToMyblog: () => void, setSignInModalOpen: (value: boolean) => void) => {
    return refreshTokenRequest(refreshToken, deviceInfo)
      .then((newAccessToken) => {
        console.log("새로운 엑세스 토큰:", newAccessToken);
        if (newAccessToken) {
          navigateToMyblog();
        } else {
          console.error('리프레쉬 토큰 갱신 실패');
          setSignInModalOpen(true);
        }
        return newAccessToken;
      })
      .catch((error) => {
        console.error('리프레쉬 토큰 갱신 실패', error);
        setSignInModalOpen(true);
        return null;
      });
  };
  
  const checkAccessToken = (accessToken: string) => {
    console.log('엑세스토큰 보내기전 :', accessToken);
    return axios.get(AAA_URL(), {
      headers: { 'Authorization': `Bearer ${accessToken}` },
      withCredentials: true
    })
    .then((response) => {
      console.log("로그인 사용자");
      return response.status === 200;
    })
    .catch((error) => {
      console.error("엑세스 토큰 인증 실패", error);
      return false;
    });
  };
  
  export const onHomeButtonClickHandler = (navigateToMyblog: () => void, setSignInModalOpen: (value: boolean) => void) => {
    const accessToken: string | undefined = getCookie('accessToken');
    const refreshToken: string | undefined = getCookie('refreshToken');
    const deviceInfo = getDeviceInfo();
  
    if (accessToken) {
      checkAccessToken(accessToken)
        .then((isValid) => {
          if (isValid) {
            navigateToMyblog();
          } else {
            if (refreshToken) {
              handleTokenRefresh(refreshToken, deviceInfo, navigateToMyblog, setSignInModalOpen);
            } else {
              console.error('비로그인!');
              setSignInModalOpen(true);
            }
          }
        })
        .catch(() => {
          console.error('비로그인!');
          setSignInModalOpen(true);
        });
    } else if (refreshToken) {
      handleTokenRefresh(refreshToken, deviceInfo, navigateToMyblog, setSignInModalOpen);
    } else {
      console.error('비로그인!');
      setSignInModalOpen(true);
    }
  };
 