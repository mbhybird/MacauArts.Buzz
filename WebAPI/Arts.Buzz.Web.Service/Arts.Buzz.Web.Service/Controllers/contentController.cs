using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class contentController : BaseApiController
    {
        // GET api/content
        public IEnumerable<content> Getcontents()
        {
            return db.content.AsEnumerable();
        }

        // GET api/content/5
        public content Getcontent(string id)
        {
            content content = db.content.Find(id);
            if (content == null)
            {
                throw new HttpResponseException(Request.CreateResponse(HttpStatusCode.NotFound));
            }

            return content;
        }

        // PUT api/content/5
        public HttpResponseMessage Putcontent(string id, content content)
        {
            if (!ModelState.IsValid)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }

            if (id != content.contentid)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest);
            }

            db.Entry(content).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK);
        }

        // POST api/content
        public HttpResponseMessage Postcontent(content content)
        {
            if (ModelState.IsValid)
            {
                db.content.Add(content);
                db.SaveChanges();

                HttpResponseMessage response = Request.CreateResponse(HttpStatusCode.Created, content);
                response.Headers.Location = new Uri(Url.Link("DefaultApi", new { id = content.contentid }));
                return response;
            }
            else
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ModelState);
            }
        }

        // DELETE api/content/5
        public HttpResponseMessage Deletecontent(string id)
        {
            content content = db.content.Find(id);
            if (content == null)
            {
                return Request.CreateResponse(HttpStatusCode.NotFound);
            }

            db.content.Remove(content);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }

            return Request.CreateResponse(HttpStatusCode.OK, content);
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}