// import React, { useState, useEffect } from 'react';
// import ReactQuill from 'react-quill';
// import 'react-quill/dist/quill.snow.css';
// import Modal from 'components/Modal/Modal';
// import CommonHeaderSection from 'sections/CommonHeaderSection/CommonHeaderSection';
// import CommonDropDownSection from 'sections/CommonDropDownSection/CommonDropDownSection';
// import { combinedLogoutHandler } from 'services/LogOutService/logOutService';
// import useNavigateHelper from 'utils/NavigationUtil/navigationUtil';
// import { fetchPostById, updateMyPost } from 'services/Auth/authService'; // Update to use updatePost
// import { HeaderProvider, useHeader } from 'services/HeaderService/HeaderService';
// import { useAuth } from 'hooks/useAuthHook';
// import { useParams } from 'react-router-dom';
// import Compressor from 'compressorjs';

// const EditPostContainer: React.FC = () => {
//   const { navigateToHome, navigateToUserBlog, navigateToCreatePost, navigateToCreateNickname, navigateToLogin } = useNavigateHelper();
//   const { dropdownOpen, toggleDropdown } = useHeader();
//   const { hasNickname, myNickname, nicknameModalOpen, setNicknameModalOpen } = useAuth();
//   const [logOutModalOpen, setLogOutModalOpen] = useState(false);
//   const [refresh, setRefresh] = useState(false);
//   const [postContent, setPostContent] = useState<any>(null);
//   const [updateSuccess, setUpdateSuccess] = useState(false);
//   const [title, setTitle] = useState('');
//   const [content, setContent] = useState('');
//   const [image, setImage] = useState<string | null>(null);

//   const { postId } = useParams<{ postId: string }>();

//   const fetchPost = async (postId: string) => {
//     try {
//       const data = await fetchPostById(postId);
//       setPostContent(data);
//       setTitle(data.title); // Pre-fill the title
//       setContent(data.content); // Pre-fill the content
//     } catch (error) {
//       console.error('Error fetching post:', error);
//     }
//   };

//   const handleUpdatePost = async () => {
//     if (!postId) {
//       console.error('Post ID is undefined.');
//       return;
//     }
//     try {
//       await updateMyPost(postId, title, content); // Use updatePost instead of insertPost
//       setUpdateSuccess(true);
//       console.log(`Title: ${title}, Content: ${content}, Image: ${image}`);
//     } catch (error) {
//       console.error('Error updating post:', error);
//     }
//   };

//   const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
//     const file = e.target.files?.[0];
//     if (file) {
//       new Compressor(file, {
//         quality: 0.6, // 압축 품질 (0에서 1 사이의 값)
//         success(result) {
//           const reader = new FileReader();
//           reader.onloadend = () => {
//             setImage(reader.result as string);
//             setContent((prevContent) => `${prevContent}<img src="${reader.result}" alt="uploaded image" />`);
//           };
//           reader.readAsDataURL(result);
//         },
//         error(err) {
//           console.error(err.message);
//         },
//       });
//     }
//   };

//   useEffect(() => {
//     if (postId) {
//       fetchPost(postId);
//     }
//   }, [postId]);

//   const nicknameModalConfirm = () => {
//     navigateToCreateNickname();
//   };

//   const handleLogOut = async () => {
//     const result = await combinedLogoutHandler(navigateToHome);
//     if (result) {
//       setLogOutModalOpen(true);
//     }
//   };

//   const logOutModalConfirm = () => {
//     setRefresh(prev => !prev);
//     window.location.reload();
//   };

//   const handleHeaderClick = () => {
//     navigateToHome();
//   };

//   return (
//     <HeaderProvider>
//       <div className="max-w-screen-2xl mx-auto">
//         <CommonHeaderSection
//           title={`${myNickname || ''}'s blog`}
//           hasNickname={hasNickname}
//           nickname={myNickname}
//           onHeaderClick={handleHeaderClick}
//           onLoginClick={navigateToLogin}
//           onNicknameClick={toggleDropdown}
//         />
//         {dropdownOpen && (
//           <CommonDropDownSection
//             onMyPageClick={() => myNickname && navigateToUserBlog(myNickname)}
//             onCreatePostClick={navigateToCreatePost}
//             onLogoutClick={handleLogOut}
//             onDeleteAccountClick={navigateToHome}
//           />
//         )}

//         <div className="bg-white p-6 rounded-lg shadow-lg">
//           <h2 className="text-2xl font-bold mb-4">Edit Post</h2>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Title</label>
//             <input
//               type="text"
//               value={title}
//               onChange={(e) => setTitle(e.target.value)}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//               placeholder="Enter post title"
//             />
//           </div>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Content</label>
//             <ReactQuill value={content} onChange={setContent} />
//           </div>
//           <div className="mb-4">
//             <label className="block text-gray-700 text-sm font-bold mb-2">Image</label>
//             <input
//               type="file"
//               onChange={handleImageChange}
//               className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
//             />
//             {image && <img src={image} alt="Preview" className="mt-2" />}
//           </div>
//           <button
//             onClick={handleUpdatePost}
//             className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
//           >
//             작성
//           </button>
//         </div>

//         <Modal
//           isOpen={nicknameModalOpen}
//           onClose={() => setNicknameModalOpen(false)}
//           onConfirm={nicknameModalConfirm}
//           message="닉네임 생성은 필수 입니다."
//         />
//         <Modal
//           isOpen={logOutModalOpen}
//           onClose={() => setLogOutModalOpen(false)}
//           onConfirm={logOutModalConfirm}
//           message="로그아웃 성공"
//         />
//         <Modal
//           isOpen={updateSuccess}
//           onClose={() => setUpdateSuccess(false)}
//           onConfirm={() => setUpdateSuccess(false)}
//           message="게시물이 성공적으로 수정되었습니다."
//         />
//       </div>
//     </HeaderProvider>
//   );
// };

// export default EditPostContainer;

import React, { useState, useEffect } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import Modal from 'components/Modal/Modal';
import CommonHeaderSection from 'sections/CommonHeaderSection/CommonHeaderSection';
import CommonDropDownSection from 'sections/CommonDropDownSection/CommonDropDownSection';
import { combinedLogoutHandler } from 'services/LogOutService/logOutService';
import useNavigateHelper from 'utils/NavigationUtil/navigationUtil';
import { fetchPostById, insertPost, updateMyPost } from 'services/Auth/authService'; // insertPost 추가
import { HeaderProvider, useHeader } from 'services/HeaderService/HeaderService';
import { useAuth } from 'hooks/useAuthHook';
import { useParams } from 'react-router-dom';
import Compressor from 'compressorjs';

const EditPostContainer: React.FC = () => {
  const { navigateToHome, navigateToUserBlog, navigateToCreatePost, navigateToCreateNickname, navigateToLogin } = useNavigateHelper();
  const { dropdownOpen, toggleDropdown } = useHeader();
  const { hasNickname, myNickname, nicknameModalOpen, setNicknameModalOpen } = useAuth();
  const [logOutModalOpen, setLogOutModalOpen] = useState(false);
  const [refresh, setRefresh] = useState(false);
  const [postContent, setPostContent] = useState<any>(null);
  const [updateSuccess, setUpdateSuccess] = useState(false);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [image, setImage] = useState<string | null>(null);

  const { postId } = useParams<{ postId: string }>();

  const fetchPost = async (postId: string) => {
    try {
      const data = await fetchPostById(postId);
      setPostContent(data);
      setTitle(data.title); // Pre-fill the title
      setContent(data.content); // Pre-fill the content
    } catch (error) {
      console.error('Error fetching post:', error);
    }
  };

  const handleSavePost = async () => {
    if (postId) {
      // Update post
      try {
        await updateMyPost(postId, title, content); // Use updatePost instead of insertPost
        setUpdateSuccess(true);
        console.log(`Title: ${title}, Content: ${content}, Image: ${image}`);
      } catch (error) {
        console.error('Error updating post:', error);
      }
    } else {
      // Insert new post
      try {
        await insertPost(title, content);
        setUpdateSuccess(true);
        console.log(`Title: ${title}, Content: ${content}, Image: ${image}`);
      } catch (error) {
        console.error('Error inserting post:', error);
      }
    }
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      new Compressor(file, {
        quality: 0.6, // 압축 품질 (0에서 1 사이의 값)
        success(result) {
          const reader = new FileReader();
          reader.onloadend = () => {
            setImage(reader.result as string);
            setContent((prevContent) => `${prevContent}<img src="${reader.result}" alt="uploaded image" />`);
          };
          reader.readAsDataURL(result);
        },
        error(err) {
          console.error(err.message);
        },
      });
    }
  };

  useEffect(() => {
    if (postId) {
      fetchPost(postId);
    }
  }, [postId]);

  const nicknameModalConfirm = () => {
    navigateToCreateNickname();
  };

  const handleLogOut = async () => {
    const result = await combinedLogoutHandler(navigateToHome);
    if (result) {
      setLogOutModalOpen(true);
    }
  };

  const logOutModalConfirm = () => {
    setRefresh(prev => !prev);
    window.location.reload();
  };

  const handleHeaderClick = () => {
    navigateToHome();
  };

  return (
    <HeaderProvider>
      <div className="max-w-screen-2xl mx-auto">
        <CommonHeaderSection
          title={`${myNickname || ''}'s blog`}
          hasNickname={hasNickname}
          nickname={myNickname}
          onHeaderClick={handleHeaderClick}
          onLoginClick={navigateToLogin}
          onNicknameClick={toggleDropdown}
        />
        {dropdownOpen && (
          <CommonDropDownSection
            onMyPageClick={() => myNickname && navigateToUserBlog(myNickname)}
            onCreatePostClick={navigateToCreatePost}
            onLogoutClick={handleLogOut}
            onDeleteAccountClick={navigateToHome}
          />
        )}

        <div className="bg-white p-6 rounded-lg shadow-lg">
          <h2 className="text-2xl font-bold mb-4">{postId ? 'Edit Post' : 'Create Post'}</h2> {/* 제목 변경 */}
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">Title</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              placeholder="Enter post title"
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">Content</label>
            <ReactQuill value={content} onChange={setContent} />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2">Image</label>
            <input
              type="file"
              onChange={handleImageChange}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            {image && <img src={image} alt="Preview" className="mt-2" />}
          </div>
          <button
            onClick={handleSavePost} // handleSavePost 함수로 변경
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          >
            {postId ? 'Update Post' : 'Create Post'} {/* 버튼 텍스트 변경 */}
          </button>
        </div>

        <Modal
          isOpen={nicknameModalOpen}
          onClose={() => setNicknameModalOpen(false)}
          onConfirm={nicknameModalConfirm}
          message="닉네임 생성은 필수 입니다."
        />
        <Modal
          isOpen={logOutModalOpen}
          onClose={() => setLogOutModalOpen(false)}
          onConfirm={logOutModalConfirm}
          message="로그아웃 성공"
        />
        <Modal
          isOpen={updateSuccess}
          onClose={() => setUpdateSuccess(false)}
          onConfirm={() => setUpdateSuccess(false)}
          message="게시물이 성공적으로 저장되었습니다."
        />
      </div>
    </HeaderProvider>
  );
};

export default EditPostContainer;
