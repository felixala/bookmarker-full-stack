import React from 'react';
import {BookmarksResponse} from "@/services/models";
import Bookmark from "@/components/bookmark";
import Pagination from "@/components/pagination";

interface BookmarksProps{
bookmarks: BookmarksResponse
}

const Bookmarks: React.FC<BookmarksProps> = ({bookmarks}) => (
    <div>
        <Pagination bookmarks = {bookmarks}/>
        {bookmarks.data.map(bookmark => <Bookmark key={bookmark.id} bookmark={bookmark}/> )}
    </div>
);

export default Bookmarks;