const DOMAIN = 'http://127.0.0.1:4040';
const API_DOMAIN = `${DOMAIN}/api/v1`;


// 인증상태관리 관련
export const SNS_SIGN_IN_URL = (type: 'kakao' | 'naver') => `${API_DOMAIN}/auth/oauth2/${type}`;
export const SIGN_IN_URL = () => `${API_DOMAIN}/auth/sign-in`;
export const SIGN_UP_URL = () => `${API_DOMAIN}/auth/sign-up`;
export const ID_CHECK_URL = () => `${API_DOMAIN}/auth/id-check`;
export const EMAIL_CERTIFICATION_URL = () => `${API_DOMAIN}/auth/email-certification`;
export const CHECK_CERTIFICATION_URL = () => `${API_DOMAIN}/auth/check-certification`;
export const NICKNAME_CHECK_URL = () => `${API_DOMAIN}/aaa/nickname-check`;
export const NICKNAME_CREATE_URL = () => `${API_DOMAIN}/aaa/nickname-create`;
export const NICKNAME_FIND_URL = () => `${API_DOMAIN}/aaa/nickname-find`;
export const LOGOUT_URL = () => `${API_DOMAIN}/aaa/logout`;
export const REFRESH_TOKEN_URL = () => `${API_DOMAIN}/aaa/refresh-token`;
export const AAA_URL = () => `${API_DOMAIN}/aaa/aaa`;
export const POST_DELETE_ACCOUNT = () => `${API_DOMAIN}/aaa/delete`;

// 게시글 관련
export const GET_ALL_POSTS = () => `${API_DOMAIN}/public/posts`;
export const GET_PUBLIC_POST = (postId : string) => `${API_DOMAIN}/public/posts/${postId}`;
export const GET_PRIVATE_POST = (postId : string) => `${API_DOMAIN}/private/posts/${postId}`;
export const CHECK_POST_OWNER = (postId : string) => `${API_DOMAIN}/private/posts/is-owner/${postId}`;
export const GET_ALL_POST_BY_NICKNAME_URL = () => `${API_DOMAIN}/public/posts/by-nickname`;
export const GET_POST_BY_NICKNAME_20_URL = () =>  `${API_DOMAIN}/public/posts/by-nickname-paged`;
export const LIKE_CLICK_URL = () => `${API_DOMAIN}/private/posts/like`;
export const POST_IS_LIKED_URL = () => `${API_DOMAIN}/private/posts/is-liked`
export const DELETE_MY_POST_URL = (postId: string) => `${API_DOMAIN}/private/posts/${postId}`;
export const POST_ADD_COMMENT = () => `${API_DOMAIN}/private/comments`;
export const POST_DELETE_COMMENT = () => `${API_DOMAIN}/private/comments/delete`;
export const POST_EDIT_COMMENT = () => `${API_DOMAIN}/private/comments/edit`;
export const POST_INSERT_POST = () => `${API_DOMAIN}/private/posts`;
export const PUT_UPDATE_POST = (postId: string) =>  `${API_DOMAIN}/private/posts/${postId}`;