import { AiFillHeart } from "react-icons/ai";

type UserBlogLikeCountProps = {
    likeCount: number;
};

const UserBlogLikeCount = ({ likeCount }: UserBlogLikeCountProps) => {
    return (
        <div className="text-red-500 flex gap-1">
            <span className="text-red-500">♥️ {likeCount}</span>
        </div>
    );
};
export default UserBlogLikeCount;

{/* <span className="text-red-500">♥️ {likeCount}</span> */}