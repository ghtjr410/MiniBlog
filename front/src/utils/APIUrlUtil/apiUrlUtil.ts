const DOMAIN = 'http://127.0.0.1:4040';
const API_DOMAIN = `${DOMAIN}/api/v1`;

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
export const GET_ALL_POSTS = () => `${API_DOMAIN}/public/posts`;
export const GET_POST = (postId : string) => `${API_DOMAIN}/public/posts/${postId}`;
export const CHECK_POST_OWNER = (postId : string) => `${API_DOMAIN}/private/posts/is-owner/${postId}`;